package com.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Activity {

    @ApiModelProperty(value = "主键")
    private int id;

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "活动简介")
    private String activityInfo;

    @ApiModelProperty(value = "活动封面")
    private String activityCover;

    @ApiModelProperty(value = "活动上限人数")
    private int activityPeople;

    @ApiModelProperty(value = "活动创始人ID")
    private  String activityAccountid;

    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd",timezone ="GMT+8")
    @ApiModelProperty(value = "活动报名开始时间")
    private Date activityCreateTime;

    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd",timezone ="GMT+8")
    @ApiModelProperty(value = "活动报名结束时间")
    private Date activityendTime;

    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd",timezone ="GMT+8")
    @ApiModelProperty(value = "begin_time")
    private Date beginTime;

    @ApiModelProperty(value = "活动价格")
    private int price;

    @ApiModelProperty(value = "活动地点")
    private String place;

    @ApiModelProperty("字段创建时间")
    private Date createTime;

    @ApiModelProperty("0:审批中；1：官方活动;2:自营活动")
    private int status;
}
