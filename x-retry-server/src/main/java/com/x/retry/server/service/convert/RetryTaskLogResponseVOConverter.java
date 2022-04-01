package com.x.retry.server.service.convert;

import com.x.retry.common.core.covert.AbstractConverter;
import com.x.retry.server.persistence.mybatis.po.RetryTaskLog;
import com.x.retry.server.web.model.response.RetryTaskLogResponseVO;

import java.util.List;

/**
 * @author: www.byteblogs.com
 * @date : 2022-02-28 09:19
 */
public class RetryTaskLogResponseVOConverter extends AbstractConverter<RetryTaskLog, RetryTaskLogResponseVO> {

    @Override
    public RetryTaskLogResponseVO convert(RetryTaskLog retryTaskLog) {
        return convert(retryTaskLog, RetryTaskLogResponseVO.class);
    }

    @Override
    public List<RetryTaskLogResponseVO> batchConvert(List<RetryTaskLog> retryTaskLogs) {
        return batchConvert(retryTaskLogs, RetryTaskLogResponseVO.class);
    }
}
