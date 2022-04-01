package com.x.retry.client.core;

import com.github.rholder.retry.Retryer;
import com.x.retry.client.core.retryer.RetryerInfo;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-03 14:54
 */
public interface RetryExecutor<BR, SR> {

    RetryerInfo getRetryerInfo();

    Object execute(Object... params);


    Retryer build(RetryExecutorParameter<BR, SR> parameter);

    <V> V call(Retryer<V> retryer, Callable<V> callable, Consumer<Throwable> retryError, Consumer<V> retrySuccess) throws Exception;

}
