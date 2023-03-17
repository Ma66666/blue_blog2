package com.blog.entity.Vo;

import lombok.Data;

import java.util.Date;

@Data
public class ApplyVo {
    private String username;
    private String accountId;
    private String headerUrl;
    private String sex;
    private String Psignature;
    private Date createTime;

}
