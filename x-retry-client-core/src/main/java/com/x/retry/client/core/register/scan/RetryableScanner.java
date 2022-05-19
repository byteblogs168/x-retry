package com.x.retry.client.core.register.scan;

import com.x.retry.client.core.BizIdGenerate;
import com.x.retry.client.core.annotation.Retryable;
import com.x.retry.client.core.Scanner;
import com.x.retry.client.core.retryer.RetryType;
import com.x.retry.client.core.retryer.RetryerInfo;
import com.x.retry.client.core.strategy.RetryMethod;
import com.x.retry.common.core.context.SpringContext;
import com.x.retry.common.core.log.LogUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-03 16:55
 */
@Component
public class RetryableScanner implements Scanner, ApplicationContextAware {

    public ApplicationContext applicationContext;

    @Override
    public List<RetryerInfo> doScan() {
        return scanRetryAbleMethod();
    }

    private List<RetryerInfo> scanRetryAbleMethod() {

        List<RetryerInfo> retryerInfoList = new ArrayList<>();
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);

            Map<Method, Retryable> annotatedMethods = null;
            try {
                annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                        (MethodIntrospector.MetadataLookup<Retryable>) method -> AnnotatedElementUtils.findMergedAnnotation(method, Retryable.class));
            } catch (Throwable ex) {
                LogUtils.error("{}重试信息加载报错：{}", beanDefinitionName, ex);
            }
            if (annotatedMethods == null || annotatedMethods.isEmpty()) {
                continue;
            }

            for (Map.Entry<Method, Retryable> methodEntry : annotatedMethods.entrySet()) {
                Method executeMethod = methodEntry.getKey();
                Retryable retryable = methodEntry.getValue();
                RetryerInfo retryableRegistrarContext = resolvingRetryable(retryable, bean, executeMethod);
                retryerInfoList.add(retryableRegistrarContext);
            }
        }

        return retryerInfoList;
    }

    private RetryerInfo resolvingRetryable(Retryable retryable, Object executor, Method executorMethodName) {

        //异常校验处理
        Class<? extends Throwable>[] include = retryable.include();
        Class<? extends Throwable>[] exclude = retryable.exclude();

        Class executorNotProxy = AopUtils.getTargetClass(executor);
        String executorClassName = executorNotProxy.getName();
        Class<? extends BizIdGenerate> bizIdGenerate = retryable.bizId();
        String bizNo = retryable.bizNo();
        RetryType retryType = retryable.retryStrategy();
        int localTimes = retryable.localTimes();
        int localInterval = retryable.localInterval();
        Class<? extends RetryMethod> retryMethod = retryable.retryMethod();

        return new RetryerInfo(retryable.scene(),
                executorClassName,
                new HashSet<>(Arrays.asList(include)),
                new HashSet<>(Arrays.asList(exclude)),
                executor,
                executorMethodName,
                retryType,
                localTimes,
                localInterval,
                bizIdGenerate,
                bizNo,
                retryMethod
        );
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }
}
