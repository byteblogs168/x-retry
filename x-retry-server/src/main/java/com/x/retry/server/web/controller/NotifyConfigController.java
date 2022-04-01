package com.x.retry.server.web.controller;

import com.x.retry.server.persistence.mybatis.mapper.NotifyConfigMapper;
import com.x.retry.server.service.NotifyConfigService;
import com.x.retry.server.web.annotation.LoginRequired;
import com.x.retry.server.web.model.response.NotifyConfigResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-03 11:32
 */
@RestController
@RequestMapping("/notify-config")
public class NotifyConfigController {

    @Autowired
    private NotifyConfigService notifyConfigService;

    @LoginRequired
    @GetMapping("list")
    public List<NotifyConfigResponseVO> getNotifyConfigList(@RequestParam("groupName") String groupName) {
        return notifyConfigService.getNotifyConfigList(groupName);
    }

}
