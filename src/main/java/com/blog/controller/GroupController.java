package com.blog.controller;


import com.blog.service.GroupService;
import com.blog.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("api/group")
@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("getChatList")
    @ApiOperation(value = "获得聊天列表")
    public Result getChatList(HttpServletRequest httpServletRequest)  {
        return Result.ok(groupService.queryGroupList(httpServletRequest));
    }
    @GetMapping("getMessageList")
    @ApiOperation(value = "获得聊天全部内容")
    public Result getMessageList(@RequestParam("chatNumber") String chatNumber, HttpServletRequest httpServletRequest){
        return Result.ok(groupService.queryMessageList(httpServletRequest,chatNumber));
    }

    @GetMapping("getUserList")
    @ApiOperation(value = "获得群用户集合")
    public Result getUserList(@RequestParam("chatNumber") String chatNumber, HttpServletRequest httpServletRequest){
        return Result.ok(groupService.queryUser(httpServletRequest,chatNumber));
    }

    @PostMapping("addMessage")
    @ApiOperation("插入聊天记录")
    public Result addMessag(@RequestParam("chatNumber") String chatNumber,
                            @RequestParam("Message") String Message,
                            HttpServletRequest httpServletRequest){
        return Result.ok(groupService.insertMessage(httpServletRequest,chatNumber,Message));
    }

    @GetMapping("deleteUser")
    @ApiOperation(value = "删除用户")
    public Result deleteUser(@RequestParam("chatNumber") String chatNumber, @RequestParam("accountId") String accountId){
        return Result.ok(groupService.deleteUser(chatNumber,accountId));
    }
    @GetMapping("insertUser")
    @ApiOperation(value = "插入用户")
    public Result insertUser(@RequestParam("chatNumber") String chatNumber,HttpServletRequest httpServletRequest,
                             @RequestParam("name") String name,
                             @RequestParam("headerUrl") String headerUrl){
        return Result.ok(groupService.insertUser(chatNumber,httpServletRequest,name,headerUrl));
    }

    @GetMapping("queryGroup2")
    @ApiOperation(value = "查询所有群聊")
    public Result queryGroup2(){
        return Result.ok(groupService.queryGroupList1());
    }

    @GetMapping("queren")
    public Result queren(@RequestParam("accountId") String accountId,
                         @RequestParam("chatNumber") String chatNumber,
                         @RequestParam("id") int id
                         ){
        return Result.ok(groupService.queren(accountId,id,chatNumber));
    }
}
