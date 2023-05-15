package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GroupMessage {

    @ApiModelProperty(value = "ID主键")
    private int id;

    @ApiModelProperty(value = "用户ID")
    private String accountId;

    @ApiModelProperty(value = "群号")
    private String chatNumber;

    @ApiModelProperty
    private String message;

    @ApiModelProperty(value ="0-未读:1-已读:2-删除")
    private int status;

    @ApiModelProperty(value = "消息创建时间")
    private Date createTime;



}
