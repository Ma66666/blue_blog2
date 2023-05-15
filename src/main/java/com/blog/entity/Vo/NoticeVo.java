package com.blog.entity.Vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class NoticeVo {

    private String id;

    @ApiModelProperty(value = "用户Id")
    private String sendAccountId;

    @ApiModelProperty(value = "用户头像")
    private String headerUrl;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "博客Id")
    private int blogId;

    private String status;

    private String user1User2;

}
