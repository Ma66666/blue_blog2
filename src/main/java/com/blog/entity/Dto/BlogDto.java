package com.blog.entity.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogDto {

    @ApiModelProperty(value = "账户编号ID")
    private String accountId;

    @ApiModelProperty(value = "博客标题")
    private String title;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @ApiModelProperty(value = "博客状态 0：草稿 1：正常 2：置顶 3删除")
    private String type;

    @ApiModelProperty(value = "话题")
    private String topic;

    @ApiModelProperty(value = "标签")
    private String tag;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "图片集合")
    private List<Object> ImgList;



}
