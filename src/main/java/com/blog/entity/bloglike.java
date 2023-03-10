package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class bloglike {
    @ApiModelProperty(value = "主键")
    private int id;

    @ApiModelProperty(value = "博客ID")
    private int blogId;

    @ApiModelProperty(value = "用户ID")
    private String accountId;

    @ApiModelProperty(value = "数据更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private int deleteType;
}
