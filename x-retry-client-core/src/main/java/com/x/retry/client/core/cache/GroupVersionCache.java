package com.x.retry.client.core.cache;

import com.x.retry.client.core.Lifecycle;
import com.x.retry.client.core.client.NettyHttpConnectClient;
import com.x.retry.client.core.client.request.ConfigHttpRequestHandler;
import com.x.retry.client.core.client.request.RequestParam;
import com.x.retry.client.core.client.response.XRetryResponse;
import com.x.retry.common.core.context.SpringContext;
import com.x.retry.common.core.log.LogUtils;
import com.x.retry.common.core.model.XRetryRequest;
import com.x.retry.server.model.dto.ConfigDTO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author: www.byteblogs.com
 * @date : 2022-05-02 21:06
 */
@Component
@Order()
public class GroupVersionCache implements Lifecycle {

    public static ConfigDTO configDTO;

    public static Integer getVersion() {
        if (Objects.isNull(configDTO)) {
            return 0;
        }
        return configDTO.getVersion();
    }

    public static Set<String> getSceneBlacklist() {
        if (Objects.isNull(configDTO)) {
            return new HashSet<>();
        }

        return configDTO.getSceneBlacklist();
    }

    public static ConfigDTO.Notify getNotifyAttribute(Integer notifyScene) {
        List<ConfigDTO.Notify> notifyList = configDTO.getNotifyList();
        for (ConfigDTO.Notify notify : notifyList) {
            if (notify.getNotifyScene().equals(notifyScene)) {
                return notify;
            }
        }

        return null;
    }


    @Override
    public void start() {

        try {
            XRetryRequest xRetryRequest = new XRetryRequest(getVersion());

            ConfigHttpRequestHandler configHttpRequestHandler = SpringContext.getBeanByType(ConfigHttpRequestHandler.class);
            XRetryResponse.cache(xRetryRequest, configHttpRequestHandler.callable());
            NettyHttpConnectClient.send(configHttpRequestHandler.method(), configHttpRequestHandler.getHttpUrl(
                    new RequestParam()), configHttpRequestHandler.body(xRetryRequest));

        } catch (Exception e) {
            LogUtils.error("同步版本失败", e);
        }
    }

    @Override
    public void close() {

    }
}
