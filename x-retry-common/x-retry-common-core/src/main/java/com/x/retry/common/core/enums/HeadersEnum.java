package com.x.retry.common.core.enums;

import lombok.Getter;

/**
 * @author: www.byteblogs.com
 * @date : 2022-03-11 19:44
 */
@Getter
public enum HeadersEnum {

    HOST_ID("host-id"),
    HOST_IP("host-ip"),
    HOST_PORT("host-port"),
    GROUP_NAME("group-name"),
    REQUEST_ID("request-id"),
        ;

    private final String key;

    HeadersEnum(String key) {
        this.key = key;
    }
}
