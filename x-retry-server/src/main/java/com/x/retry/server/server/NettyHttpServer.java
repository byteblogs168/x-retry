package com.x.retry.server.server;

import com.x.retry.server.config.SystemProperties;
import com.x.retry.server.exception.XRetryServerException;
import com.x.retry.server.support.Lifecycle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-07 15:54
 */
@Component
@Slf4j
public class NettyHttpServer implements Runnable, Lifecycle {

    @Autowired
    private SystemProperties systemProperties;

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ThreadPoolExecutor serverHandlerPool = new ThreadPoolExecutor(60, 300, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2000), r -> new Thread(r, "x-retry-serverHandlerPool-" + r.hashCode()), (r, executor) -> {
            throw new XRetryServerException("x-retry thread pool is EXHAUSTED!");
        });

        try {
            // start server
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new IdleStateHandler(0, 0, 30 * 3, TimeUnit.SECONDS))  // beat 3N, close if idle
                                    .addLast(new HttpServerCodec())
                                    .addLast(new HttpObjectAggregator(5 * 1024 * 1024))  // merge request & reponse to FULL
                                    .addLast(new NettyHttpServerHandler(serverHandlerPool));
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // bind
            ChannelFuture future = bootstrap.bind(systemProperties.getNettyPort()).sync();

            log.info("------> x-retry remoting server start success, nettype = {}, port = {}", NettyHttpServer.class.getName(), 1788);

            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            if (e instanceof InterruptedException) {
                log.info("--------> x-retry remoting server stop.");
            } else {
                log.error("--------> x-retry remoting server error.", e);
            }
        } finally {

            // stop
            try {
                serverHandlerPool.shutdown();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            try {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void start() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void close() {

    }
}
