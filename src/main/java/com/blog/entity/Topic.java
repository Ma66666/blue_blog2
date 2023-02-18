package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Topic {
    @ApiModelProperty(value = "ID主键")
    private int id;
    @ApiModelProperty(value = "标签")
    private String topic;
    @ApiModelProperty(value = "标签说明")
    private String explain;

    @ApiModelProperty(value = "图标")
    private String icon1;
}
