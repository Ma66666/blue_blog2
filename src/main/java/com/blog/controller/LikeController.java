package com.blog.controller;

import com.blog.service.LikeService;
import com.blog.util.ExceptionHandler.BlogException;
import com.blog.util.GetTokenAccountId;
import com.blog.util.result.Result;
import com.blog.util.result.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags="点赞关注相关")
@RestController
@RequestMapping("api/Like")
@CrossOrigin
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private GetTokenAccountId getTokenAccountId;

    @ApiOperation("用户点赞博客")
    @PostMapping(value="blogLike")
    public Result blogLike(@RequestParam(value = "blogId") int blog,HttpServletRequest httpServletRequest){
        String AccountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        likeService.Bloglike(blog,AccountId);
        return Result.ok("ok");
    }
    @ApiOperation("用户收藏博客")
    @PostMapping(value = "blogCollect")
    public Result blogCollect(@RequestParam(value = "blogId") int blog,HttpServletRequest httpServletRequest){
        String AccountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        likeService.BlogCollect(blog,AccountId);
        return Result.ok("ok");
    }


    @ApiOperation("用户点赞评论")
    @PostMapping(value="CommentLike")
    public Result CommentLike(@RequestParam(value = "commentId") int id,@RequestParam(value = "blogId") int blogId,HttpServletRequest httpServletRequest){
        String AccountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        likeService.CommentLike(id,blogId,AccountId);
        return Result.ok("ok");
    }

    //关注用户
    @ApiOperation("用户关注用户")
    @PostMapping(value = "followUser")
    public Result followUser(@RequestParam(value = "BeLikeAccountId") String BeLikeAccountId,
                             HttpServletRequest httpServletRequest ){
               if (BeLikeAccountId == ""){
                   throw new BlogException(ResultCodeEnum.DATA_ERROR);
               }
        String AccountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
               likeService.follow(AccountId,BeLikeAccountId);
               return Result.ok();
    }
    //取关用户
    @ApiOperation("用户取关")
    @PostMapping(value = "unfollowUser")
    public Result unfollowUser(@RequestParam(value = "BeLikeAccountId") String BeLikeAccountId,
                             HttpServletRequest httpServletRequest ){
        if (BeLikeAccountId == ""){
            throw new BlogException(ResultCodeEnum.DATA_ERROR);
        }
        String AccountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        likeService.unfollow(AccountId,BeLikeAccountId);
        return Result.ok();
    }
    //获得用户关注状态
    @ApiOperation("获得用户关注状态")
    @GetMapping(value = "getUserLikeType")
    public Result getUserLikeType(@RequestParam(value = "BeLikeAccountId") String BeLikeAccountId,
                                  HttpServletRequest httpServletRequest ){
        String AccountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        if (AccountId.equals(BeLikeAccountId)){
            return Result.ok(3);
        }
        return Result.ok(likeService.hasLike(AccountId,BeLikeAccountId));
    }

}
