package com.blog.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BlogListVo {
    @ApiModelProperty(value = "博客ID")
    private int id;

    @ApiModelProperty(value = "用户昵称")
    private String username;

    @ApiModelProperty(value = "博客标题")
    private String title;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @ApiModelProperty(value = "博客热度")
    private String hot;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "用户头像")
    private String headerUrl;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;



}
