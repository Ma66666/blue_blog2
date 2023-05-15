package com.blog.entity.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GroupListVo {
    @ApiModelProperty(value = "ID主键")
    private int id;

    @ApiModelProperty(value = "群名")
    private String name;

    @ApiModelProperty(value = "最后一条消息")
    private String message;

    @ApiModelProperty(value = "群号")
    private String chatNumber;

    @ApiModelProperty(value = "用户名")
    private String username;

    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd",timezone ="GMT+8")
    @ApiModelProperty(value = "最后一条消息的创建时间")
    private Date createTime;

    @ApiModelProperty(value = "群头像")
    private String headerUrl;

    @ApiModelProperty(value = "未读消息数量")
    private String count;

    @ApiModelProperty
    private int status;
}
