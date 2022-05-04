package com.x.retry.common.core.alarm.strategy;

import com.x.retry.common.core.alarm.AlarmContext;
import com.x.retry.common.core.alarm.DingDingAttribute;
import com.x.retry.common.core.enums.AlarmTypeEnum;
import com.x.retry.common.core.util.DingDingUtils;
import com.x.retry.common.core.util.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: www.byteblogs.com
 * @date : 2021-11-25 09:20
 */
@Component
public class DingdingAlarm extends AbstractAlarm<AlarmContext> {

    @Override
    public Integer getAlarmType() {
        return AlarmTypeEnum.DING_DING.getValue();
    }

    @Override
    public boolean asyncSendMessage(AlarmContext context) {

        DingDingAttribute dingDingAttribute = JsonUtil.parseObject(context.getNotifyAttribute(), DingDingAttribute.class);
        threadPoolExecutor.execute(() ->
                DingDingUtils.sendMessage(DingDingUtils.buildSendRequest(context.getTitle(), context.getText()), dingDingAttribute.getDingDingUrl()));

        return true;
    }

    @Override
    public boolean syncSendMessage(AlarmContext context) {
        DingDingAttribute dingDingAttribute = JsonUtil.parseObject(context.getNotifyAttribute(), DingDingAttribute.class);
        return DingDingUtils.sendMessage(DingDingUtils.buildSendRequest(context.getTitle(), context.getText()), dingDingAttribute.getDingDingUrl());
    }

    @Override
    public boolean asyncSendMessage(List<AlarmContext> alarmContexts) {

        for (AlarmContext alarmContext : alarmContexts) {
            asyncSendMessage(alarmContext);
        }

        return Boolean.TRUE;
    }
}

