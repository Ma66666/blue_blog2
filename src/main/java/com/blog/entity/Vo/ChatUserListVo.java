package com.blog.entity.Vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ChatUserListVo {

    @ApiModelProperty(value = "ID主键")
    private int id;

    @ApiModelProperty(value = "用户ID")
    private String accountId;

    @ApiModelProperty(value = "用户头像")
    private String headerUrl;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "聊天中最后一条信息")
    private String message;

    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd",timezone ="GMT+8")
    @ApiModelProperty(value = "最后一条消息的创建时间")
    private Date createTime;

    @ApiModelProperty(value = "发、接消息人之间的联系")
    private String user1User2;

    @ApiModelProperty(value = "未读消息数量")
    private String count;
}