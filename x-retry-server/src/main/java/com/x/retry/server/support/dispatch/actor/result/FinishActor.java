package com.x.retry.server.support.dispatch.actor.result;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.x.retry.common.core.enums.RetryStatusEnum;
import com.x.retry.common.core.log.LogUtils;
import com.x.retry.common.core.util.Assert;
import com.x.retry.server.akka.ActorGenerator;
import com.x.retry.server.exception.XRetryServerException;
import com.x.retry.server.persistence.mybatis.mapper.RetryTaskLogMapper;
import com.x.retry.server.persistence.mybatis.po.RetryTask;
import com.x.retry.server.persistence.mybatis.po.RetryTaskLog;
import com.x.retry.server.persistence.support.RetryTaskAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 重试完成执行器
 * 1、更新重试任务
 * 2、记录重试日志
 *
 * @author www.byteblogs.com
 * @date 2021-10-30
 * @since 2.0
 */
@Component("FinishActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class FinishActor extends AbstractActor  {

    public static final String BEAN_NAME = "FinishActor";

    @Autowired
    @Qualifier("retryTaskAccessProcessor")
    private RetryTaskAccess<RetryTask> retryTaskAccess;

    @Autowired
    private RetryTaskLogMapper retryTaskLogMapper;

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(RetryTask.class, retryTask->{
            LogUtils.info(log, "FinishActor params:[{}]", retryTask);

            retryTask.setRetryStatus(RetryStatusEnum.FINISH.getStatus());

            try {
                retryTaskAccess.updateRetryTask(retryTask);

                // 重试成功回调客户端
                ActorRef actorRef = ActorGenerator.callbackRetryResultActor();
                actorRef.tell(retryTask, actorRef);
            }catch (Exception e) {
                LogUtils.error(log, "更新重试任务失败", e);
            } finally {

                getContext().stop(getSelf());

                // 记录重试日志
                PageDTO<RetryTaskLog> retryTaskLogPageDTO = retryTaskLogMapper.selectPage(new PageDTO<>(1, 1),
                        new LambdaQueryWrapper<RetryTaskLog>()
                                .eq(RetryTaskLog::getBizId, retryTask.getBizId())
                                .orderByDesc(RetryTaskLog::getId));

                List<RetryTaskLog> records = retryTaskLogPageDTO.getRecords();
                if (!CollectionUtils.isEmpty(records)) {
                    RetryTaskLog retryTaskLog = records.get(0);
                    retryTaskLog.setRetryStatus(retryTask.getRetryStatus());
                    Assert.isTrue(1 ==  retryTaskLogMapper.updateById(retryTaskLog),
                            new XRetryServerException("更新重试日志失败"));
                }
            }


        }).build();
    }

}
