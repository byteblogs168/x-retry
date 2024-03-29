package com.x.retry.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.x.retry.common.core.enums.RetryResultStatusEnum;
import com.x.retry.common.core.enums.RetryStatusEnum;
import com.x.retry.server.config.RequestDataHelper;
import com.x.retry.server.exception.XRetryServerException;
import com.x.retry.server.persistence.mybatis.po.RetryTaskLog;
import com.x.retry.server.support.strategy.WaitStrategies;
import com.x.retry.server.web.model.base.PageResult;
import com.x.retry.server.persistence.mybatis.mapper.RetryTaskMapper;
import com.x.retry.server.persistence.mybatis.po.RetryTask;
import com.x.retry.server.service.RetryTaskService;
import com.x.retry.server.service.convert.RetryTaskResponseVOConverter;
import com.x.retry.server.web.model.request.RetryTaskQueryVO;
import com.x.retry.server.web.model.request.RetryTaskRequestVO;
import com.x.retry.server.web.model.response.RetryTaskResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author www.byteblogs.com
 * @date 2022-02-27
 * @since 2.0
 */
@Service
public class RetryTaskServiceImpl implements RetryTaskService {

    @Autowired
    private RetryTaskMapper retryTaskMapper;

    RetryTaskResponseVOConverter retryTaskResponseVOConverter = new RetryTaskResponseVOConverter();

    @Override
    public PageResult<List<RetryTaskResponseVO>> getRetryTaskPage(RetryTaskQueryVO queryVO) {

        PageDTO<RetryTask> pageDTO = new PageDTO<>(queryVO.getPage(), queryVO.getSize());

        LambdaQueryWrapper<RetryTask> retryTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(queryVO.getGroupName())) {
            retryTaskLambdaQueryWrapper.eq(RetryTask::getGroupName, queryVO.getGroupName());
        } else {
            return new PageResult<>(pageDTO, new ArrayList<>());
        }

        if (StringUtils.isNotBlank(queryVO.getSceneName())) {
            retryTaskLambdaQueryWrapper.eq(RetryTask::getSceneName, queryVO.getSceneName());
        }
        if (StringUtils.isNotBlank(queryVO.getBizNo())) {
            retryTaskLambdaQueryWrapper.eq(RetryTask::getBizNo, queryVO.getBizNo());
        }
        if (StringUtils.isNotBlank(queryVO.getBizId())) {
            retryTaskLambdaQueryWrapper.eq(RetryTask::getBizId, queryVO.getBizId());
        }
        if (Objects.nonNull(queryVO.getRetryStatus())) {
            retryTaskLambdaQueryWrapper.eq(RetryTask::getRetryStatus, queryVO.getRetryStatus());
        }

        RequestDataHelper.setPartition(queryVO.getGroupName());

        retryTaskLambdaQueryWrapper.select(RetryTask::getId, RetryTask::getBizNo, RetryTask::getBizId,
                RetryTask::getGroupName, RetryTask::getNextTriggerAt, RetryTask::getRetryCount,
                RetryTask::getRetryStatus, RetryTask::getUpdateDt, RetryTask::getSceneName);
        pageDTO = retryTaskMapper.selectPage(pageDTO, retryTaskLambdaQueryWrapper.orderByDesc(RetryTask::getCreateDt));
        return new PageResult<>(pageDTO, retryTaskResponseVOConverter.batchConvert(pageDTO.getRecords()));
    }

    @Override
    public RetryTaskResponseVO getRetryTaskById(String groupName, Long id) {
        RequestDataHelper.setPartition(groupName);
        RetryTask retryTask = retryTaskMapper.selectById(id);
        return retryTaskResponseVOConverter.convert(retryTask);
    }

    @Override
    public int updateRetryTaskStatus(RetryTaskRequestVO retryTaskRequestVO) {

        RetryStatusEnum retryStatusEnum = RetryStatusEnum.getByStatus(retryTaskRequestVO.getRetryStatus());
        if (Objects.isNull(retryStatusEnum)) {
            throw new XRetryServerException("重试状态错误");
        }

        RequestDataHelper.setPartition(retryTaskRequestVO.getGroupName());
        RetryTask retryTask = retryTaskMapper.selectById(retryTaskRequestVO.getId());
        if (Objects.isNull(retryTask)) {
            throw new XRetryServerException("未查询到重试任务");
        }

        retryTask.setRetryStatus(retryTaskRequestVO.getRetryStatus());
        retryTask.setGroupName(retryTaskRequestVO.getGroupName());

        // 若恢复重试则需要重新计算下次触发时间
        if (RetryStatusEnum.RUNNING.getStatus().equals(retryStatusEnum.getStatus())) {
            retryTask.setNextTriggerAt(WaitStrategies.randomWait(1, TimeUnit.SECONDS, 60, TimeUnit.SECONDS).computeRetryTime(null));
        }

        RequestDataHelper.setPartition(retryTaskRequestVO.getGroupName());
        return retryTaskMapper.updateById(retryTask);
    }
}
