package com.blog.controller;

import com.blog.service.ChatMessageService;
import com.blog.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;


@Api(tags="登录注册相关")
@RestController
@RequestMapping("api/message")
@CrossOrigin
public class MessageController {
    @Autowired
    private ChatMessageService chatMessageService;


    @GetMapping("getChatList")
    @ApiOperation(value = "获得聊天列表")
    public Result getChatList( HttpServletRequest httpServletRequest)  {
        return Result.ok(chatMessageService.queryChatUserListVo(httpServletRequest));
    }
    @GetMapping("getMessageList")
    @ApiOperation(value = "获得聊天全部内容")
    public Result getMessageList(@RequestParam("user1_user2") String user1_user2, HttpServletRequest httpServletRequest){
        return Result.ok(chatMessageService.queryMessageList(httpServletRequest,user1_user2));
    }

    @PostMapping("addMessage")
    @ApiOperation("插入聊天记录")
    public Result addMessag(@RequestParam("toAccountId") String toAccountId,
                            @RequestParam("Message") String Message,
                            HttpServletRequest httpServletRequest){

        return Result.ok(chatMessageService.insertMessage(httpServletRequest,toAccountId,Message));
    }

}
