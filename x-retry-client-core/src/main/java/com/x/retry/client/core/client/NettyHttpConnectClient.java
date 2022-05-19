package com.x.retry.client.core.client;

import cn.hutool.core.util.IdUtil;
import com.x.retry.client.core.Lifecycle;
import com.x.retry.client.core.cache.GroupVersionCache;
import com.x.retry.client.core.config.XRetryProperties;
import com.x.retry.common.core.context.SpringContext;
import com.x.retry.common.core.enums.HeadersEnum;
import com.x.retry.common.core.log.LogUtils;
import com.x.retry.common.core.util.HostUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-07 18:24
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NettyHttpConnectClient implements Lifecycle, ApplicationContextAware {

    private static final String HOST_ID = IdUtil.simpleUUID();
    private static final String HOST_IP = HostUtils.getIp();

    private ApplicationContext applicationContext;
    private static Channel channel;
    private static NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
    private static Bootstrap bootstrap = new Bootstrap();
    @Override
    public void start() {

        try {
            XRetryProperties xRetryProperties = applicationContext.getBean(XRetryProperties.class);
            XRetryProperties.ServerConfig server = xRetryProperties.getServer();
            final NettyHttpConnectClient thisClient = this;
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(server.getHost(), server.getPort())
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new IdleStateHandler(0, 0, 30, TimeUnit.SECONDS))
                                    .addLast(new HttpClientCodec())
                                    .addLast(new HttpObjectAggregator(5 * 1024 * 1024))
                                    .addLast(new NettyHttpClientHandler(thisClient));
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channel = channelFuture.channel();

        } catch (Exception e) {
            log.error("客户端发送异常", e);
        }
    }

    @Override
    public void close() {
        nioEventLoopGroup.shutdownGracefully();
    }

    public static void connect(){
        channel = bootstrap.connect().addListener((ChannelFutureListener) future -> {
            if (future.cause() != null){
                LogUtils.debug("operationComplete", future.cause());
            }
        }).channel();
    }

    public static void send(HttpMethod method, String url, String body) throws InterruptedException {

        if (Objects.isNull(channel)) {
            LogUtils.debug("channel is null");
            return;
        }

        // 配置HttpRequest的请求数据和一些配置信息
        FullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_0, method, url, Unpooled.wrappedBuffer(body.getBytes(StandardCharsets.UTF_8)));

        Environment environment = SpringContext.applicationContext.getBean(Environment.class);

        request.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                //开启长连接
                .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                //设置传递请求内容的长度
                .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes())
                .set(HeadersEnum.HOST_ID.getKey(), HOST_ID)
                .set(HeadersEnum.HOST_IP.getKey(), HOST_IP)
                .set(HeadersEnum.GROUP_NAME.getKey(), XRetryProperties.getGroup())
                .set(HeadersEnum.HOST_PORT.getKey(), environment.getProperty("server.port", Integer.class))
                .set(HeadersEnum.VERSION.getKey(), GroupVersionCache.getVersion())
        ;

        //发送数据
        channel.writeAndFlush(request).sync();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
