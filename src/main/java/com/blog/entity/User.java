package com.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int id;

    private String accountId;

    private String username;

    private String password;

    private String email;

    private int status;

    private String headerUrl;

    private Date createTime;

}
