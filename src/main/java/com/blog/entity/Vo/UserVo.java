package com.blog.entity.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserVo {
    private String username;
    private String accountId;
    private String headerUrl;
    private String sex;
    private String pSignature;
    private Date   createTime;
    private String status;
}
