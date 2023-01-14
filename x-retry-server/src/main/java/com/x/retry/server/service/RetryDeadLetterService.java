package com.x.retry.server.service;

import com.x.retry.server.web.model.base.PageResult;
import com.x.retry.server.web.model.request.RetryDeadLetterQueryVO;
import com.x.retry.server.web.model.response.RetryDeadLetterResponseVO;

import java.util.List;

/**
 * @author: www.byteblogs.com
 * @date : 2022-02-28 09:34
 */
public interface RetryDeadLetterService {

    PageResult<List<RetryDeadLetterResponseVO>> getRetryDeadLetterPage(RetryDeadLetterQueryVO queryVO);

    RetryDeadLetterResponseVO getRetryDeadLetterById(String groupName, Long id);

    boolean rollback(String groupName, Long id);

    boolean deleteById(String groupName, Long id);
}
