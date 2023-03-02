package com.blog.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//粉丝列表Vo
@Data
public class LikeVo {
    @ApiModelProperty(value = "用户ID")
    private String accountId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户头像")
    private String headerUrl;

    @ApiModelProperty(value = "用户状态")
    private int Liketype;

}
