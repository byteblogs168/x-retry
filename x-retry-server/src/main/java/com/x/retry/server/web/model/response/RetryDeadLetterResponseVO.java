package com.x.retry.server.web.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: www.byteblogs.com
 * @date : 2022-02-28 09:36
 */
@Data
public class RetryDeadLetterResponseVO {

    private Long id;

    private String groupName;

    private String sceneName;

    private String bizId;

    private String bizNo;

    private String executorName;

    private String argsStr;

    private String extAttrs;

    private LocalDateTime createDt;

}
