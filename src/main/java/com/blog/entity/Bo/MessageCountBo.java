package com.blog.entity.Bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageCountBo {

    @ApiModelProperty(value = "未读数量")
    private int count1;

    @ApiModelProperty(value = "某个会话")
    private String user1User2;
}
