package com.blog.entity.Vo;

import com.blog.entity.Blog;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BlogVo {

    @ApiModelProperty(value = "账户编号ID")
    private String accountId;

    @ApiModelProperty(value = "用户昵称")
    private String username;

    @ApiModelProperty(value = "用户头像")
    private String headerUrl;

    @ApiModelProperty(value = "博客标题")
    private String title;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @ApiModelProperty(value = "博客热度")
    private String hot;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "封面")
    private String cover;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片1")
    private String image1;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片2")
    private String image2;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片3")
    private String image3;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片4")
    private String image4;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片5")
    private String image5;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片6")
    private String image6;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片7")
    private String image7;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片8")
    private String image8;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "图片9")
    private String image9;

    public List<String > list = new ArrayList<>();
}
