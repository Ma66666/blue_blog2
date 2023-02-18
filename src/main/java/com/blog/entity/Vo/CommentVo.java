package com.blog.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CommentVo {
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

    @ApiModelProperty(value = "用户头像")
    private String headerUrl;
    
    @ApiModelProperty(value = "用户昵称")
    private String username;

    @ApiModelProperty(value = "点赞总数")
    private int likeCount;

    @ApiModelProperty(value = "用户点赞状态 0：未点赞,1已点赞")
    private int likeType;

    private  List<CommentVo> commentVoList ;

    public CommentVo() {
      commentVoList = new ArrayList<>();
    }
    public void addCommentVo(CommentVo commentVo){
        commentVoList.add(commentVo);
    }


}
