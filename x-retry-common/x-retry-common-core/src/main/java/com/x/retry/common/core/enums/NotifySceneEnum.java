package com.x.retry.common.core.enums;

import lombok.Getter;

/**
 * 通知场景枚举
 *
 * @author: www.byteblogs.com
 * @date : 2021-11-24 18:18
 */
@Getter
public enum NotifySceneEnum {

    MAX_RETRY(1, "重试数量超过阈值", NodeTypeEnum.SERVER),

    MAX_RETRY_ERROR(2, "重试失败数量超过阈值", NodeTypeEnum.SERVER),

    CLIENT_REPORT_ERROR(3,"客户端上报失败", NodeTypeEnum.CLIENT),

    CLIENT_COMPONENT_ERROR(4,"客户端组件异常", NodeTypeEnum.CLIENT);

    /**
     * 通知场景
     */
    private final int notifyScene;

    /**
     * 触发通知节点类型
     */
    private final NodeTypeEnum nodeType;

    /**
     * 描述
     */
    private final String desc;

    NotifySceneEnum(int notifyScene, String desc, NodeTypeEnum nodeType) {
        this.notifyScene = notifyScene;
        this.desc = desc;
        this.nodeType = nodeType;
    }

    /**
     * 获取通知场景
     *
     * @param notifyScene 场景
     * @param nodeType 触发通知节点类型
     * @return this
     */
    public static NotifySceneEnum getNotifyScene(int notifyScene, NodeTypeEnum nodeType) {
        for (NotifySceneEnum sceneEnum : NotifySceneEnum.values()) {
            if (sceneEnum.getNotifyScene() == notifyScene && sceneEnum.nodeType.getType().equals(nodeType.getType())) {
                return sceneEnum;
            }
        }

        return null;
    }


}
