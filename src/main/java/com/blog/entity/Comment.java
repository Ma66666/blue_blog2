package com.blog.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

//评论实体
@Data
public class Comment {

    @ApiModelProperty(value = "ID主键")
    private int id;

    @ApiModelProperty(value = "父Id   0:评论 ;其它:为一级评论的Id")
    private int parentId ;

    @ApiModelProperty(value = "博客ID")
    private int blogId;

    @ApiModelProperty(value = "用户UID")
    private String accountId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "用户类型 0：普通用户，1：作者，2:管理员")
    private int userType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "回复用户ID")
    private String replyUserId;

    @ApiModelProperty(value = "被回复用户的名称")
    private String replyUserName;

    @ApiModelProperty(value = "点赞总数")
    private int likeCount;

    private int status;




}
