package com.x.retry.server.support.dispatch.actor.scan;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.x.retry.common.core.log.LogUtils;
import com.x.retry.client.model.DispatchRetryResultDTO;
import com.x.retry.common.core.model.Result;
import com.x.retry.server.akka.ActorGenerator;
import com.x.retry.server.config.SystemProperties;
import com.x.retry.server.persistence.mybatis.mapper.ServerNodeMapper;
import com.x.retry.server.persistence.mybatis.po.GroupConfig;
import com.x.retry.server.persistence.mybatis.po.RetryTask;
import com.x.retry.server.persistence.mybatis.po.SceneConfig;
import com.x.retry.server.persistence.mybatis.po.ServerNode;
import com.x.retry.server.persistence.support.ConfigAccess;
import com.x.retry.server.persistence.support.RetryTaskAccess;
import com.x.retry.server.support.ClientLoadBalance;
import com.x.retry.server.support.IdempotentStrategy;
import com.x.retry.server.support.WaitStrategy;
import com.x.retry.server.support.allocate.client.ClientLoadBalanceManager;
import com.x.retry.server.support.context.MaxAttemptsPersistenceRetryContext;
import com.x.retry.server.support.dispatch.DispatchService;
import com.x.retry.server.support.retry.RetryBuilder;
import com.x.retry.server.support.retry.RetryExecutor;
import com.x.retry.server.support.strategy.FilterStrategies;
import com.x.retry.server.support.strategy.StopStrategies;
import com.x.retry.server.support.strategy.WaitStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author www.byteblogs.com
 * @date 2021-10-30
 * @since 2.0
 */
@Component("ScanGroupActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScanGroupActor extends AbstractActor {

    @Autowired
    @Qualifier("retryTaskAccessProcessor")
    private RetryTaskAccess<RetryTask> retryTaskAccessProcessor;

    @Autowired
    @Qualifier("bitSetIdempotentStrategyHandler")
    private IdempotentStrategy<String, Integer> idempotentStrategy;

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    @Qualifier("configAccessProcessor")
    private ConfigAccess configAccess;

    @Autowired
    private ServerNodeMapper serverNodeMapper;

    public static final String BEAN_NAME = "ScanGroupActor";

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(GroupConfig.class, config -> {

            try {
                doScan(config);
            } catch (Exception e) {
                LogUtils.error("数据扫描器处理异常 [{}]", config, e);
            }

        }).build();

    }

    /**
     * 扫描数据
     *
     * @param groupConfig
     */
    private void doScan(GroupConfig groupConfig) {

        LocalDateTime defLastAt = LocalDateTime.now().minusDays(systemProperties.getLastDays());

        String groupName = groupConfig.getGroupName();
        LocalDateTime lastAt = DispatchService.LAST_AT_MAP.getOrDefault(groupName, defLastAt);

        // 扫描当前Group 待重试的数据
        List<RetryTask> list = retryTaskAccessProcessor.listAvailableTasks(groupName, lastAt, systemProperties.getRetryPullPageSize());

        if (!CollectionUtils.isEmpty(list)) {

            DispatchService.LAST_AT_MAP.put(groupConfig.getGroupName(), list.get(list.size() - 1).getCreateDt());

            for (RetryTask retryTask : list) {

                retryCountIncrement(retryTask);

                MaxAttemptsPersistenceRetryContext<Result<DispatchRetryResultDTO>> retryContext = new MaxAttemptsPersistenceRetryContext<>();
                retryContext.setRetryTask(retryTask);
                retryContext.setSceneBlacklist(configAccess.getBlacklist(groupName));
                retryContext.setServerNode(getServerNode(retryTask));

                RetryExecutor<Result<DispatchRetryResultDTO>> executor = RetryBuilder.<Result<DispatchRetryResultDTO>>newBuilder()
                        .withStopStrategy(StopStrategies.stopResultStatus())
                        .withWaitStrategy(getWaitWaitStrategy(groupName, retryTask.getSceneName()))
                        .withFilterStrategy(FilterStrategies.delayLevelFilter())
                        .withFilterStrategy(FilterStrategies.bitSetIdempotentFilter(idempotentStrategy))
                        .withFilterStrategy(FilterStrategies.sceneBlackFilter())
                        .withFilterStrategy(FilterStrategies.checkAliveClientPodFilter())
                        .withFilterStrategy(FilterStrategies.rateLimiterFilter())
                        .withRetryContext(retryContext)
                        .build();

                if (!executor.filter()) {

                    // 不触发重试
                    ActorRef actorRef = ActorGenerator.noRetryActor();
                    actorRef.tell(executor, actorRef);

                    continue;
                }

                productExecUnitActor(executor);
            }
        } else {
            // 数据为空则休眠5s
            try {
                Thread.sleep((DispatchService.PERIOD / 2) * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            DispatchService.LAST_AT_MAP.put(groupName, defLastAt);
        }
    }

    private WaitStrategy getWaitWaitStrategy(String groupName, String sceneName) {

        SceneConfig sceneConfig = configAccess.getSceneConfigByGroupNameAndSceneName(groupName, sceneName);
        Integer backOff = sceneConfig.getBackOff();

        return WaitStrategies.WaitStrategyEnum.getWaitStrategy(backOff);
    }

    private void retryCountIncrement(RetryTask retryTask) {
        Integer retryCount = retryTask.getRetryCount();
        retryTask.setRetryCount(++retryCount);
    }

    /**
     * 获取分配的节点
     */
    public ServerNode getServerNode(RetryTask retryTask) {

        GroupConfig groupConfig = configAccess.getGroupConfigByGroupName(retryTask.getGroupName());
        List<ServerNode> serverNodes = serverNodeMapper.selectList(new LambdaQueryWrapper<ServerNode>().eq(ServerNode::getGroupName, retryTask.getGroupName()));

        if (CollectionUtils.isEmpty(serverNodes)) {
            return null;
        }

        ClientLoadBalance clientLoadBalanceRandom = ClientLoadBalanceManager.getClientLoadBalance(groupConfig.getRouteKey());

        String hostIp = clientLoadBalanceRandom.route(retryTask.getGroupName(), new TreeSet<>(serverNodes.stream().map(ServerNode::getHostIp).collect(Collectors.toSet())));
        return serverNodes.stream().filter(s -> s.getHostIp().equals(hostIp)).findFirst().get();
    }

    private void productExecUnitActor(RetryExecutor<Result<DispatchRetryResultDTO>> retryExecutor) {
        String groupIdHash = retryExecutor.getRetryContext().getRetryTask().getGroupName();
        Long retryId = retryExecutor.getRetryContext().getRetryTask().getId();
        idempotentStrategy.set(groupIdHash, retryId.intValue());

        ActorRef execUnitActor = ActorGenerator.execUnitActor();
        // 将扫描到的数据tell 到执行单元中
        execUnitActor.tell(retryExecutor, execUnitActor);
    }

}
