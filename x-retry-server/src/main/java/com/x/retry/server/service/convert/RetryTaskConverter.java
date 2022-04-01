package com.x.retry.server.service.convert;

import com.x.retry.common.core.covert.AbstractConverter;
import com.x.retry.server.model.dto.RetryTaskDTO;
import com.x.retry.server.persistence.mybatis.po.RetryTask;
import com.x.retry.server.support.strategy.WaitStrategies;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: www.byteblogs.com
 * @date : 2021-11-26 15:22
 */
public class RetryTaskConverter extends AbstractConverter<RetryTaskDTO, RetryTask> {

    @Override
    public RetryTask convert(RetryTaskDTO retryTaskDTO) {

        RetryTask retryTask = convert(retryTaskDTO, RetryTask.class);
        retryTask.setCreateDt(LocalDateTime.now());
        retryTask.setUpdateDt(LocalDateTime.now());

        if (StringUtils.isBlank(retryTask.getExtAttrs())) {
            retryTask.setExtAttrs(StringUtils.EMPTY);
        }

        retryTask.setNextTriggerAt(WaitStrategies.randomWait(1, TimeUnit.SECONDS, 60, TimeUnit.SECONDS).computeRetryTime(null));
        return retryTask;
    }

    @Override
    public List<RetryTask> batchConvert(List<RetryTaskDTO> retryTaskDTOList) {
        return batchConvert(retryTaskDTOList, RetryTask.class);
    }
}
