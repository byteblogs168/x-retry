package com.x.retry.client.core.strategy;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-03 15:43
 */
public interface RetryMethod {

    Object doExecute(Object[] params);

}
