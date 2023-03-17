package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChatList {
    @ApiModelProperty(value = "ID主键")
    private int id;

    @ApiModelProperty(value = "发消息人的ID")
    private String user1_accountId;

    @ApiModelProperty(value = "接收消息人的ID")
    private String user2_accountId;

    @ApiModelProperty(value = "发、接消息人之间的联系")
    private String user1User2;
}
