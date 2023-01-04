package com.blog.service;

import com.blog.entity.User;
import com.blog.entity.Vo.loginVo;
import com.blog.entity.Vo.registerVo;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface UserService {
    public User findUserByAccountId(String accountId);//获取用户信息

    public Map<String,Object> register(registerVo registerVo);//注册

    public boolean send(String phoneNum, String templateCode, String code,String code2);//发送注册验证码

    public boolean login(loginVo loginuser);//登录

}
