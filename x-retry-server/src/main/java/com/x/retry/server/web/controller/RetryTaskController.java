package com.x.retry.server.web.controller;

import com.x.retry.server.service.RetryTaskService;
import com.x.retry.server.web.annotation.LoginRequired;
import com.x.retry.server.web.model.base.PageResult;
import com.x.retry.server.web.model.request.RetryTaskQueryVO;
import com.x.retry.server.web.model.request.RetryTaskRequestVO;
import com.x.retry.server.web.model.response.RetryTaskResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author www.byteblogs.com
 * @date 2022-02-27
 * @since 2.0
 */
@RestController
@RequestMapping("/retry-task")
public class RetryTaskController {

    @Autowired
    private RetryTaskService retryTaskService;

    @LoginRequired
    @GetMapping("list")
    public PageResult<List<RetryTaskResponseVO>> getRetryTaskPage(RetryTaskQueryVO queryVO) {
        return retryTaskService.getRetryTaskPage(queryVO);
    }

    @LoginRequired
    @GetMapping("{id}")
    public RetryTaskResponseVO getRetryTaskById(@RequestParam("groupName") String groupName,
                                                @PathVariable("id") Long id) {
        return retryTaskService.getRetryTaskById(groupName, id);
    }

    @LoginRequired
    @PutMapping("status")
    public int updateRetryTaskStatus(@RequestBody RetryTaskRequestVO retryTaskRequestVO) {
        return retryTaskService.updateRetryTaskStatus(retryTaskRequestVO);
    }
}
