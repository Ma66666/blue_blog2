package com.blog.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MessageListVo {

    @ApiModelProperty(value = "ID主键")
    private int id;

    @ApiModelProperty(value = "用户名称")
    private String accountId;

    @ApiModelProperty(value = "用户头像")
    private String headerUrl;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "在前端显示的位置")
    private String position;






}
