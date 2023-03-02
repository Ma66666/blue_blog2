package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Url {

    @ApiModelProperty(value = "ID主键")
    private int id;
    @ApiModelProperty(value = "url名")
    private String name;
    @ApiModelProperty(value = "url")
    private String url;
    @ApiModelProperty(value = "图标")
    private String iconfont;
}
