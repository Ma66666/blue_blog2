package com.blog.controller;

import com.blog.dao.UserMapper;
import com.blog.entity.User;
import com.blog.entity.Vo.loginVo;
import com.blog.entity.Vo.registerVo;
import com.blog.result.Result;
import com.blog.result.ResultCodeEnum;
import com.blog.result.blog_token;
import com.blog.service.UserService;
import com.blog.util.GetSetRedis;
import com.blog.util.RandomFour;
import com.blog.util.SendSms;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
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

    @Autowired
    private GetSetRedis getSetRedis;

    @Autowired
    private RandomFour randomFour;

    @Autowired
    private UserService userService;



    @GetMapping("sendSMS")
    public Result send(@RequestParam("phone") String phone){
        System.out.println(phone);
        String x = randomFour.getFour();
        getSetRedis.setValue(phone,x);
        return Result.ok(userService.send(phone,"",x,"1"));
    }
    @ApiOperation(value = "注册")
    @PostMapping("register")
    public Result register(@RequestBody registerVo registervo){
        if (registervo.getCode().equals(getSetRedis.getValue(registervo.getPhone()))) {
            return Result.ok(userService.register(registervo));
        }
        else {
            return Result.fail("验证码错误，插入失败");
        }
    }


    @PostMapping("login")
    public Result login (@RequestBody loginVo loginuser,
                         HttpServletRequest request,
                         HttpServletResponse response){
           String token = null;
            if (userService.login(loginuser)){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("accountId", loginuser.getAccountId());
                token = blog_token.createJavaWebToken(m);
                System.out.println("登录成功");
                return Result.ok(token);
            }
        System.out.println("登录失败");
        return Result.fail(ResultCodeEnum.LOGIN_MOBLE_ERROR);
    }






}
