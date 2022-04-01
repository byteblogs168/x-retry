package com.example.demo;

import com.x.retry.client.core.client.request.BeatHttpRequestHandler;
import com.x.retry.client.core.strategy.RetryMethod;
import com.x.retry.common.core.model.Result;
import com.x.retry.common.core.util.JsonUtil;
import com.x.retry.server.model.dto.ConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-07 14:07
 */
@Component
@Slf4j
public class MyRetryMethod implements RetryMethod {

    @Override
    public Object doExecute(Object... params) {
        log.info("测试自定义重试方法 MyRetryMethod params:[{}]", JsonUtil.toJsonString(params));
        return "测试成功";
    }

}
