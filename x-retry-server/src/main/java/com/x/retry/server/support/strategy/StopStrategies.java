package com.x.retry.server.support.strategy;

import com.x.retry.client.model.DispatchRetryResultDTO;
import com.x.retry.common.core.enums.RetryResultStatusEnum;
import com.x.retry.common.core.enums.StatusEnum;
import com.x.retry.common.core.model.Result;
import com.x.retry.server.support.RetryContext;
import com.x.retry.server.support.StopStrategy;
import com.x.retry.server.support.context.MaxAttemptsPersistenceRetryContext;

import java.util.Objects;

/**
 * 生成 {@link StopStrategy} 实例.
 *
 * @author: www.byteblogs.com
 * @date : 2021-11-29 19:22
 */
public class StopStrategies {

    private StopStrategies() {
    }

    /**
     * 根据重试结果终止
     *
     * @return {@link ResultStatusStopStrategy} 重试结果停止策略
     */
    public static StopStrategy stopResultStatus() {
        return new ResultStatusStopStrategy();
    }

    /**
     * 根据重试结果集判断是否应该停止
     *
     * 1、{@link Result#getStatus()} 不为1 则继续重试
     * 2、根据{@link Result#getData()}中的statusCode判断是否停止
     */
    private static final class ResultStatusStopStrategy implements StopStrategy {

        @Override
        public boolean shouldStop(RetryContext retryContext) {

            MaxAttemptsPersistenceRetryContext<Result<DispatchRetryResultDTO>> context =
                    (MaxAttemptsPersistenceRetryContext<Result<DispatchRetryResultDTO>>) retryContext;

            Result<DispatchRetryResultDTO> response = context.getCallResult();
            DispatchRetryResultDTO data = response.getData();
            if (StatusEnum.YES.getStatus() != response.getStatus() || Objects.isNull(data)) {
                return Boolean.FALSE;
            }

            Integer statusCode = data.getStatusCode();
            Integer status = RetryResultStatusEnum.getRetryResultStatusEnum(statusCode).getStatus();
            return RetryResultStatusEnum.SUCCESS.getStatus().equals(status) || RetryResultStatusEnum.STOP.getStatus().equals(status);
        }
    }

}
