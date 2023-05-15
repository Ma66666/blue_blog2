package com.blog.controller;


import com.alibaba.fastjson.JSON;
import com.blog.entity.Vo.UserVo;
import com.blog.service.FriendService;
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

    @Autowired
    private FriendService friendService;

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

    @ApiOperation(value = "获得好友列表")
    @GetMapping("/getFriendList")
    public Result getFriendList(HttpServletRequest httpServletRequest){
        return Result.ok(friendService.getFriendList(httpServletRequest));
    }

    @ApiOperation(value = "查看申请列表")
    @GetMapping("/getApplyList")
    public Result getApplyList(HttpServletRequest httpServletRequest){
        return Result.ok(friendService.getApplyList(httpServletRequest));
    }

    @ApiOperation(value = "更新申请状态")
    @PostMapping("/updateApplyStatus")
    public Result updateApplyStatus(HttpServletRequest httpServletRequest,
                                    @RequestParam("user_accountId") String user_accountId,
                                    @RequestParam("status") int status
                                    ){
        System.out.println(status);
        friendService.updateApplyStatus(httpServletRequest,user_accountId,status);
        return Result.ok("更新成功");
    }
    @ApiOperation(value = "更新申请状态")
    @PostMapping("/deleteFriend")
    public Result deleteFriend(HttpServletRequest httpServletRequest, @RequestParam("user_accountId") String user_accountId){
        friendService.deleteFriend(httpServletRequest,user_accountId);
        return Result.ok("删除成功");
    }

    @ApiOperation(value = "查询用户的好友申请数量")
    @GetMapping("/getApplyCount")
    public Result getApplyCount(HttpServletRequest httpServletRequest){
       return Result.ok(friendService.queryApplyCount(httpServletRequest));
    }
    @GetMapping("getEsUseristByCondition")
    public Result EsUserListByCondition(@RequestParam(value = "page")int pagenum,
                                        @RequestParam("size") int size,
                                        HttpServletRequest httpServletRequest,
                                        @RequestParam("condition")  String condition
    ){
        return Result.ok(userService.queryUserList(condition));
    }


}
