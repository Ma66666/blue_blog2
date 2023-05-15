package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Activitylist {

    @ApiModelProperty(value = "主键")
    private int id;

    @ApiModelProperty(value = "用户ID")
    private String accountId;

    @ApiModelProperty(value = "活动ID")
    private int activityId;

    @ApiModelProperty(value = "0：未缴费；1缴费")
    private int status;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "订单编号")
    private String orderId;
}
