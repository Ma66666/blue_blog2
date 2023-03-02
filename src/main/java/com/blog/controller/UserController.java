package com.blog.controller;


import com.alibaba.fastjson.JSON;
import com.blog.entity.Vo.UserVo;
import com.blog.util.BlogToken;
import com.blog.util.ExceptionHandler.BlogException;
import com.blog.util.result.Result;
import com.blog.service.UserService;
import com.blog.util.GetSetRedis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


import static com.blog.util.result.ResultCodeEnum.DATA_ERROR;

@Api(tags="用户信息相关")
@RestController
@RequestMapping("api/User")
@CrossOrigin
public class UserController {
    @Autowired
    private GetSetRedis getSetRedis;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取个人用户信息")
    @GetMapping("getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        UserVo userVo = JSON.parseObject(getSetRedis.getToken(token),UserVo.class);
        return Result.ok(userVo);
    }
    @PostMapping("updateUserInfo")
    public Result updateUserInfo(@RequestBody UserVo userVo,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return Result.ok(userService.updateUserInfo(token,userVo));
    }
    @ApiOperation(value = "更换头像")
    @PostMapping("/upload")
    public Result uploadHeader(@RequestParam("file") MultipartFile headerImage, HttpServletRequest request) throws IOException {
        if (headerImage == null) {
            throw new BlogException(DATA_ERROR);
        }

        if (userService.updateHeaderUrl(headerImage,request)){
            return Result.ok("更换成功");
        }        return Result.fail("更换失败");
    }




}
