package com.blog.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.Date;

//用户实体
@Data
public class User {
    @ApiModelProperty(value = "用户ID")
    private int id;

    @ApiModelProperty(value = "账户编号ID")
    private String accountId;

    @ApiModelProperty(value = "用户昵称")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "用户电话")
    private String phone;

    @ApiModelProperty(value = "状态 0：正常 1：vip 2：封号 3:管理员")
    private String status;

    @ApiModelProperty(value ="男:1 女:2 无性别:3" )
    private String  sex;

    @ApiModelProperty(value = "用户头像")
    private String headerUrl;


    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "个性签名")
    private String pSignature;

}
