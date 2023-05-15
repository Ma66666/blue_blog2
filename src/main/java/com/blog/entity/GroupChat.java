package com.blog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GroupChat {

    @ApiModelProperty(value = "ID主键")
    private int id;

    @ApiModelProperty(value = "用户ID")
    private String accountId;

    @ApiModelProperty(value = "群号")
    private String chatNumber;

    @ApiModelProperty(value ="状态：1：群主 2：普通成员 3：被删除成员 4：管理员")
    private int status;

    @ApiModelProperty(value = "用户头像")
    private String headerUrl;

    @ApiModelProperty(value = "0:正常 1：逻辑删除")
    private int deleteType;

    @ApiModelProperty(value = "用户创建时间")
    private Date createTime;

    @ApiModelProperty(value = "群名")
    private String name;

    @ApiModelProperty(value = "未读数量")
    private int num;

}
