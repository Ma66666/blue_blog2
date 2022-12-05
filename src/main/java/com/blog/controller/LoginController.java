package com.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.blog.dao.UserMapper;
import com.blog.entity.User;
import com.blog.entity.Vo.loginUser;
import com.blog.result.Result;
import com.blog.result.blog_token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/login")
@CrossOrigin
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("login")
    public Result login (@RequestBody loginUser loginuser,
                         HttpServletRequest request,
                         HttpServletResponse response){

//        JSONObject jsonObject = new JSONObject();
        String token = null;
        User user = userMapper.selectByAccountId(loginuser.getAccountId());
        if (user!=null){
//            String s = CommunityUtil.md5(password)+user.getSalt();
            if (loginuser.getPassword().equals(user.getPassword())){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("accountId", user.getAccountId());
                token = blog_token.createJavaWebToken(m);
                response.addHeader(loginuser.getAccountId(),token);
                return Result.ok(token);
            }
        }
        return Result.fail("账号密码出错");
    }
}
