package com.blog.entity.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentDto {
    @ApiModelProperty(value = "父Id   0:评论 ;其它:为一级评论的Id")
    private int parentId ;

    @ApiModelProperty(value = "博客ID")
    private int blogId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "回复用户ID")
    private String replyUserId;

    @ApiModelProperty(value = "被回复用户的名称")
    private String replyUserName;


}
