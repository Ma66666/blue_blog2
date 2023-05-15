package com.blog.entity.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ActivityListVo {
    @ApiModelProperty(value = "主键")
    private int id;

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "活动名称")
    private String activityId;

    @ApiModelProperty(value = "活动简介")
    private String activityInfo;

    @ApiModelProperty(value = "活动封面")
    private String activityCover;

    @ApiModelProperty(value = "活动创始人ID")
    private  String activityAccountId;


    @ApiModelProperty(value = "begin_time")
    private Date beginTime;

    @ApiModelProperty(value = "活动价格")
    private int price;

    @ApiModelProperty(value = "活动地点")
    private String place;

    @ApiModelProperty("字段创建时间")
    private Date createTime;

    @ApiModelProperty("0:未支付；1：已支付;3:过期")
    private int status;

    @ApiModelProperty(value = "订单编号")
    private String orderId;
}
