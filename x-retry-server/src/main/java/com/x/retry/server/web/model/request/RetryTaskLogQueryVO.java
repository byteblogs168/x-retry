package com.x.retry.server.web.model.request;

import com.x.retry.server.web.model.base.BaseQueryVO;
import lombok.Data;

/**
 * @author: www.byteblogs.com
 * @date : 2022-02-28 09:08
 */
@Data
public class RetryTaskLogQueryVO extends BaseQueryVO {

    private String groupName;

    private String sceneName;

    private String bizNo;

    private String bizId;
}
