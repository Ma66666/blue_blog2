package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 好友类实体
 */
@Data
public class Friend {
    @ApiModelProperty(value = "ID主键")
    private int id;
    @ApiModelProperty(value = "用户ID")

    private String userAccountId;

    @ApiModelProperty(value = "好友Id")
    private String friendAccountId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "用户状态 0：好友 1：拉黑")
    private int status ;


}
