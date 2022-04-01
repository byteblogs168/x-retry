package com.x.retry.client.core.init;

import com.x.retry.client.core.Lifecycle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 重试对账系统启动监听器
 *
 * @author: www.byteblogs.com
 * @date : 2021-11-19 19:00
 */
@Component
@Slf4j
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private List<Lifecycle> lifecycleList;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("X-RETRY-CLIENT-RETRY 启动");
        lifecycleList.forEach(Lifecycle::start);
        log.info("X-RETRY-CLIENT-RETRY 启动成功");
    }
}
