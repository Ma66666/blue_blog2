package com.blog.controller;

import com.blog.service.LikeService;
import com.blog.util.ExceptionHandler.BlogException;
import com.blog.util.GetTokenAccountId;
import com.blog.util.result.Result;
import com.blog.util.result.ResultCodeEnum;
import io.swagger.annotations.Api;
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
    //点赞博客
    @PostMapping(value="blogLike")
    public Result blogLike(@RequestParam(value = "blogId") int blog,HttpServletRequest httpServletRequest){
        String AccountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        likeService.Bloglike(blog,AccountId);
        return Result.ok("ok");
    }
    //收藏博客
    @PostMapping(value = "blogCollect")
    public Result blogCollect(@RequestParam(value = "blogId") int blog,HttpServletRequest httpServletRequest){
        String AccountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        likeService.BlogCollect(blog,AccountId);
        return Result.ok("ok");
    }

    //点赞评论
    @PostMapping(value="CommentLike")
    public Result CommentLike(@RequestParam(value = "commentId") int id,HttpServletRequest httpServletRequest){
        String AccountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        likeService.CommentLike(id,AccountId);
        return Result.ok("ok");
    }

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
