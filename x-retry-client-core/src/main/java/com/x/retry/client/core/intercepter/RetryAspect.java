package com.x.retry.client.core.intercepter;

import cn.hutool.core.util.StrUtil;
import com.x.retry.client.core.annotation.Retryable;
import com.x.retry.client.core.cache.GroupVersionCache;
import com.x.retry.client.core.config.XRetryProperties;
import com.x.retry.client.core.exception.XRetryClientException;
import com.x.retry.client.core.retryer.RetryerResultContext;
import com.x.retry.client.core.strategy.RetryStrategy;
import com.x.retry.common.core.alarm.Alarm;
import com.x.retry.common.core.alarm.AlarmContext;
import com.x.retry.common.core.alarm.AltinAlarmFactory;
import com.x.retry.common.core.enums.NotifySceneEnum;
import com.x.retry.common.core.enums.RetryResultStatusEnum;
import com.x.retry.common.core.log.LogUtils;
import com.x.retry.common.core.util.EnvironmentUtils;
import com.x.retry.server.model.dto.ConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-03 11:41
 */
@Aspect
@Component
@Slf4j
public class RetryAspect {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static String retryErrorMoreThresholdTextMessageFormatter =
            "<font face=\"微软雅黑\" color=#ff0000 size=4>{}环境 重试组件异常</font>  \r\n" +
                    "> 名称:{}  \r\n" +
                    "> 时间:{}  \r\n" +
                    "> 异常:{}  \n"
            ;

    @Autowired
    @Qualifier("localRetryStrategies")
    private RetryStrategy retryStrategy;
    @Autowired
    private AltinAlarmFactory altinAlarmFactory;

    @Around("@annotation(com.x.retry.client.core.annotation.Retryable)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String traceId = UUID.randomUUID().toString();

        LogUtils.debug("进入 aop [{}]", traceId);
        Retryable retryable = getAnnotationParameter(point);
        String executorClassName = point.getTarget().getClass().getName();
        String methodEntrance = getMethodEntrance(retryable, executorClassName);
        if (StrUtil.isBlank(RetrySiteSnapshot.getMethodEntrance())) {
            RetrySiteSnapshot.setMethodEntrance(methodEntrance);
        }

        Throwable throwable = null;
        Object result = null;
        try {
            result = point.proceed();
        } catch (Throwable t) {
            throwable = t;
        } finally {

            LogUtils.debug("开始进行重试 aop [{}]", traceId);
            // 入口则开始处理重试
            doHandlerRetry(point, traceId, retryable, executorClassName, methodEntrance, throwable);
        }

        LogUtils.debug("aop 结果处理 traceId:[{}] result:[{}] ", traceId, result, throwable);
        if (throwable != null) {
            throw throwable;
        } else {
            return result;
        }

    }

    private void doHandlerRetry(ProceedingJoinPoint point, String traceId, Retryable retryable, String executorClassName, String methodEntrance, Throwable throwable) {

        if (!RetrySiteSnapshot.isMethodEntrance(methodEntrance)
                || RetrySiteSnapshot.isRunning()
                || Objects.isNull(throwable)
                // 重试流量不开启重试
                || RetrySiteSnapshot.isRetryFlow()
                // 下游响应不重试码，不开启重试
                || RetrySiteSnapshot.isRetryForStatusCode()
        ) {
            LogUtils.info("校验不通过不开启重试 methodEntrance:[{}] isRunning:[{}] throwable:[{}] isRetryFlow:[{}] isRetryForStatusCode:[{}]" ,
                    !RetrySiteSnapshot.isMethodEntrance(methodEntrance),
                    RetrySiteSnapshot.isRunning(),
                    Objects.isNull(throwable),
                    RetrySiteSnapshot.isRetryFlow(),
                    RetrySiteSnapshot.isRetryForStatusCode()
            );
            return;
        }

        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            // 无事务, 开启重试
            openRetry(point, traceId, retryable, executorClassName, throwable);
            return;
        }

        // 存在事物
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCompletion(int status) {
                // 有事务开启重试
                openRetry(point, traceId, retryable, executorClassName, throwable);
            }
        });
    }

    private void openRetry(ProceedingJoinPoint point, String traceId, Retryable retryable, String executorClassName, Throwable throwable) {

        try {

            RetryerResultContext context = retryStrategy.openRetry(retryable.scene(), executorClassName, point.getArgs());
            LogUtils.info("本地重试结果 message:[{}]", context);
            if (RetryResultStatusEnum.SUCCESS.getStatus().equals(context.getRetryResultStatusEnum().getStatus())) {
                LogUtils.debug("aop 结果成功 traceId:[{}] result:[{}]", traceId, context.getResult());
            }

        } catch (Exception e) {
            LogUtils.error("重试组件处理异常，{}", e);

            // 预警
            sendMessage(e);

        } finally {
            RetrySiteSnapshot.removeAll();
        }
    }

    private void sendMessage(Exception e) {

        try {
            ConfigDTO.Notify notifyAttribute = GroupVersionCache.getNotifyAttribute(NotifySceneEnum.CLIENT_COMPONENT_ERROR.getNotifyScene());
            if (Objects.nonNull(notifyAttribute)) {
                AlarmContext context = AlarmContext.build()
                        .text(retryErrorMoreThresholdTextMessageFormatter,
                                EnvironmentUtils.getActiveProfile(),
                                XRetryProperties.getGroup(),
                                LocalDateTime.now().format(formatter),
                                e.getMessage())
                        .title("重试组件异常:[{}]", XRetryProperties.getGroup())
                        .notifyAttribute(notifyAttribute.getNotifyAttribute());

                Alarm<AlarmContext> alarmType = altinAlarmFactory.getAlarmType(notifyAttribute.getNotifyType());
                alarmType.asyncSendMessage(context);
            }
        } catch (Exception e1) {
            LogUtils.error("客户端发送组件异常告警失败", e1);
        }

    }

    public String getMethodEntrance(Retryable retryable, String executorClassName) {

        if (Objects.isNull(retryable)) {
            return StrUtil.EMPTY;
        }

        return retryable.scene().concat("_").concat(executorClassName);
    }

    private Retryable getAnnotationParameter(ProceedingJoinPoint point) {
        String methodName = point.getSignature().getName();
        Class<?> classTarget = point.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) point.getSignature()).getParameterTypes();
        Method objMethod = null;
        try {
            objMethod = classTarget.getMethod(methodName, par);
        } catch (NoSuchMethodException e) {
            throw new XRetryClientException("注解配置异常：[{}}", methodName);
        }
        return objMethod.getAnnotation(Retryable.class);
    }
}
