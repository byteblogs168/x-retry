package com.x.retry.server.web.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: www.byteblogs.com
 * @date : 2022-02-25 13:42
 */
@Data
public class GroupConfigResponseVO {

    private Long id;

    private String groupName;

    private Integer groupStatus;

    private Integer groupPartition;

    private Integer routeKey;

    private Integer version;

    private String description;

    private List<String> onlinePodList;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;
}
