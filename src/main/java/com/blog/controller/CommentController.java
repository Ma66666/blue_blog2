package com.blog.controller;

import com.blog.entity.Dto.CommentDto;
import com.blog.service.CommentService;
import com.blog.util.GetTokenAccountId;
import com.blog.util.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags="评论功能")
@RestController
@RequestMapping("api/comment")
@CrossOrigin
public class CommentController {
    @Resource
    private GetTokenAccountId getTokenAccountId;

    @Resource
    private CommentService commentService;

    //发表评论
    @PostMapping(value="sendComment")
    public Result sendComment(@RequestBody CommentDto commentDto, HttpServletRequest httpServletRequest){
        //此处要抛异常，以后补充
        String AccountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        commentService.insertComment(commentDto,AccountId);
        return  Result.ok();
    }

}
