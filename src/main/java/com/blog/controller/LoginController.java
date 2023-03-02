package com.blog.controller;

import com.alibaba.fastjson.JSON;
import com.blog.dao.UserMapper;
import com.blog.entity.Vo.UserVo;
import com.blog.entity.Vo.loginVo;
import com.blog.entity.Vo.RegisterVo;
import com.blog.util.ExceptionHandler.BlogException;
import com.blog.util.result.Result;
import com.blog.util.BlogToken;
import com.blog.service.UserService;
import com.blog.util.GetSetRedis;
import com.blog.util.RandomFour;
import com.blog.util.result.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Api(tags="登录注册相关")
@RestController
@RequestMapping("api/login")
@CrossOrigin
public class LoginController {


    @Autowired
    private GetSetRedis getSetRedis;

    @Autowired
    private RandomFour randomFour;

    @Autowired
    private UserService userService;
    @Value("${tx.key.templateId1}")
    private String templateId1;
    @Value(("${tx.key.templateId2}"))
    private String templateId2;



    @GetMapping("sendSMS")
    @ApiOperation(value = "登录验证码")
    public Result send(@RequestParam("phone") String phone){
        if (phone==null){
            throw new BlogException(ResultCodeEnum.DATA_ERROR);
        }
        System.out.println(phone);
        String x = randomFour.getFour();
        getSetRedis.setValue(phone,x);
        return Result.ok(userService.send(phone,templateId1,x,"1"));
    }
    @GetMapping("sendSMS2")
    @ApiOperation(value = "注册验证码")
    public Result send2(@RequestParam("phone") String phone){
        if (phone==null){
            throw new BlogException(ResultCodeEnum.DATA_ERROR);
        }
        System.out.println(phone);
        String x = randomFour.getFour();
        getSetRedis.setRegister(phone,x);
        return Result.ok(userService.send(phone,templateId2,x,"1"));
    }

    @ApiOperation(value = "注册")
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registervo){
        if (registervo == null){
            throw new BlogException(ResultCodeEnum.DATA_ERROR);
        }
        System.out.println(registervo.getPhone());
        System.out.println(getSetRedis.getRegister(registervo.getPhone()));
        if (registervo.getCode().equals(getSetRedis.getRegister(registervo.getPhone()))) {
            if (userService.register(registervo).containsKey("phoneMsg")){
                return Result.fail(userService.register(registervo).get("phoneMsg"));
            }
            return Result.ok(userService.register(registervo));
        }
        else {
            return Result.fail("验证码错误");
        }
    }

    @ApiOperation("登录")
    @PostMapping("login")
    public Result login (@RequestBody loginVo loginuser){
        if (loginuser == null){
            throw new BlogException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(userService.login(loginuser));
    }

}
