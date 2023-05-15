package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;

@Data
public class ChatMessage {

    @ApiModelProperty(value = "ID主键")
    private int id;

    @ApiModelProperty(value = "发消息人的ID")
    private String sendAccountId;

    @ApiModelProperty(value = "接收消息人的ID")
    private String toAccountId;

    @ApiModelProperty(value = "发、接消息人之间的联系")
    private String user1User2;

    @ApiModelProperty(value = "发送内容")
    private String message;
    @ApiModelProperty(value = "创建时间")
     private Date createTime;
    @ApiModelProperty(value = "状态：0未读,1以读,2删除")
    private int status;

    @ApiModelProperty(value = "0-消息;1-赞消息;2-回复消息;3-关注消息;4-系统通知")
    private int type;

    @ApiModelProperty(value = "博客Id")
    private int blogId;

    @ApiModelProperty(value = "评论Id")
    private int commentId;


}
