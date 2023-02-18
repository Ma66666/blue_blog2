package com.blog.controller;

import com.blog.service.*;
import com.blog.util.GetTokenAccountId;
import com.blog.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags="博客展示页面相关接口")
@RestController
@RequestMapping("api/blogShow")
@CrossOrigin
public class BlogShowController {
    @Autowired
    private BlogService blogService;

    @Resource
    private GetTokenAccountId getTokenAccountId;

    @Resource
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Resource
    private SortService sortService;
    //获取博客和评论信息
    @GetMapping( "GetBlogAndComment")
    public Result GetBlogAndComment(@RequestParam(value = "blogId") int blogId, HttpServletRequest httpServletRequest
    ){
        String accountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        Map<String,Object> map = new HashMap<>();
        map.put("blog",blogService.QueryBlog(blogId,accountId));
        map.put("comment",commentService.queryComment(blogId,0,accountId));
        return Result.ok(map);
    }
    //主页获得所有博客
    @GetMapping(value = "GetBlogList")
    public Result GetBlogList(){
        return Result.ok(blogService.QueryBlogList());
    }

    //博客详情页获得评论数
    @GetMapping(value = "GetCommentCount")
    public Result GetCommentCountt(@RequestParam(value = "blogId") int blogId){
        return Result.ok(commentService.queryCommentCount(blogId));
    }
    @ApiOperation(value = "获取用户信息")
    @GetMapping("getOtherUserInfo")
    public Result getOtherUserInfo(@RequestParam(value = "accountId") String accountId){
        return Result.ok(userService.queryUserInfo(accountId));
    }
    @ApiOperation(value = "社区排序方式")
    @GetMapping("getSortList")
    public Result getSortList(){
        return Result.ok(sortService.getSortList());
    }
}
