package com.x.retry.client.core.client.request;

import com.x.retry.common.core.model.NettyResult;
import com.x.retry.common.core.model.XRetryRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.util.function.Consumer;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-07 16:23
 */
public interface HttpRequestHandler {

    HttpMethod method();

    String getHttpUrl(RequestParam requestParam);

    Consumer<NettyResult> callable();

     String body(XRetryRequest retryRequest);

}
