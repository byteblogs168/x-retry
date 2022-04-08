package com.x.retry.client.core.retryer;

import com.x.retry.common.core.enums.RetryResultStatusEnum;
import lombok.Data;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-04 14:52
 */
@Data
public class RetryerResultContext {

    private Object result;

    private RetryerInfo retryerInfo;

    private RetryResultStatusEnum retryResultStatusEnum;

    private String message;

    private Throwable throwable;

}
