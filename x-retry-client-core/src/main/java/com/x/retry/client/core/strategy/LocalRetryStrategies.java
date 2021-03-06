package com.x.retry.client.core.strategy;

import com.github.rholder.retry.*;
import com.google.common.base.Predicate;
import com.x.retry.client.core.RetryExecutor;
import com.x.retry.client.core.RetryExecutorParameter;
import com.x.retry.client.core.exception.XRetryClientException;
import com.x.retry.client.core.intercepter.RetrySiteSnapshot;
import com.x.retry.client.core.report.ReportHandler;
import com.x.retry.client.core.retryer.RetryType;
import com.x.retry.client.core.retryer.RetryerInfo;
import com.x.retry.client.core.retryer.RetryerResultContext;
import com.x.retry.common.core.enums.RetryResultStatusEnum;
import com.x.retry.common.core.log.LogUtils;
import com.x.retry.common.core.model.XRetryHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-03 14:38
 */
@Component
@Slf4j
public class LocalRetryStrategies extends AbstractRetryStrategies {

    @Autowired
    private ReportHandler reportHandler;
    @Autowired(required = false)
    private PlatformTransactionManager platformTransactionManager;

    @Override
    public boolean supports(int stage, RetryType retryType) {
        return RetrySiteSnapshot.EnumStage.LOCAL.getStage() == stage ;
    }

    @Override
    protected void setStage() {
        RetrySiteSnapshot.setStage(RetrySiteSnapshot.EnumStage.LOCAL.getStage());
    }

    @Override
    protected Consumer<Object> doRetrySuccessConsumer(RetryerResultContext context) {
        return o -> {
            LogUtils.debug("doRetrySuccessConsumer ????????????");
        };
    }

    @Override
    protected void error(RetryerResultContext context) {
        context.setRetryResultStatusEnum(RetryResultStatusEnum.FAILURE);
    }

    @Override
    protected boolean preValidator(RetryerInfo retryerInfo, RetryerResultContext resultContext) {
        if (RetrySiteSnapshot.isRunning()) {
            resultContext.setRetryResultStatusEnum(RetryResultStatusEnum.FAILURE);
            resultContext.setMessage("??????????????????????????? ??????: ?????????????????????????????????");
            return false;
        }

        if (RetrySiteSnapshot.isRetryForStatusCode()) {
            resultContext.setRetryResultStatusEnum(RetryResultStatusEnum.FAILURE);
            resultContext.setMessage("??????????????????????????? ??????: ????????????????????????");
            return false;
        }

        return true;
    }

    @Override
    protected void unexpectedError(Exception e, RetryerResultContext retryerResultContext) {
        retryerResultContext.setRetryResultStatusEnum(RetryResultStatusEnum.FAILURE);
    }

    @Override
    protected void success(RetryerResultContext retryerResultContext) {
        retryerResultContext.setRetryResultStatusEnum(RetryResultStatusEnum.SUCCESS);
    }

    @Override
    public Consumer<Throwable> doGetRetryErrorConsumer(RetryerInfo retryerInfo, Object[] params) {
        return throwable -> {
            // ?????????????????????
            log.info("??????????????????????????????????????? scene:[{}]", retryerInfo.getScene());
            // ?????????????????????
            if (RetryType.LOCAL_REMOTE.name().equals(retryerInfo.getRetryType().name())){
                // ??????
                log.debug("?????? scene:[{}]", retryerInfo.getScene());
                reportHandler.report(retryerInfo.getScene(), retryerInfo.getExecutorClassName(), params);
            }
        };
    }

    @Override
    public Callable doGetCallable(RetryExecutor<WaitStrategy, StopStrategy> retryExecutor, Object... params) {

        RetryerInfo retryerInfo = retryExecutor.getRetryerInfo();
        RetryType retryType = retryerInfo.getRetryType();
        switch (retryType) {
            // ????????????????????????????????????_??????????????????????????????
            case ONLY_LOCAL:
            case LOCAL_REMOTE:
                return () -> {
                    if (TransactionSynchronizationManager.isActualTransactionActive()) {
                        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
                        TransactionStatus transaction = platformTransactionManager.getTransaction(def);
                        Object execute;
                        try {
                            execute = retryExecutor.execute(params);
                            platformTransactionManager.commit(transaction);
                        } catch (Exception e) {
                            platformTransactionManager.rollback(transaction);
                            throw e;
                        }

                        return execute;
                    } else {
                       return retryExecutor.execute(params);
                    }
                };
            case ONLY_REMOTE:
                // ????????????????????????????????????
                log.debug("?????? scene:[{}]", retryerInfo.getScene());
                return () -> {
                    reportHandler.report(retryerInfo.getScene(), retryerInfo.getExecutorClassName(), params);
                    RetrySiteSnapshot.setStage(RetrySiteSnapshot.EnumStage.REMOTE.getStage());
                    return null;
                };
            default:
                throw new XRetryClientException("?????????????????? [{}]", retryType.name());
        }

    }

    @Override
    public RetryExecutorParameter<WaitStrategy, StopStrategy> getRetryExecutorParameter(RetryerInfo retryerInfo) {

        return new RetryExecutorParameter<WaitStrategy, StopStrategy>() {

            @Override
            public Predicate<Throwable> exceptionPredicate() {
                return throwable -> LocalRetryStrategies.super.validate(throwable.getClass(), retryerInfo);
            }

            @Override
            public WaitStrategy backOff() {
                return WaitStrategies.fixedWait(retryerInfo.getLocalInterval(), TimeUnit.SECONDS);
            }

            @Override
            public StopStrategy stop() {
                return StopStrategies.stopAfterAttempt(retryerInfo.getLocalTimes());
            }

            @Override
            public List<RetryListener> getRetryListeners() {
                return Collections.singletonList(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        LogUtils.error("x-retry ??????????????????[{}]?????????", attempt.getAttemptNumber());
                    }
                });
            }

        };
    }
}
