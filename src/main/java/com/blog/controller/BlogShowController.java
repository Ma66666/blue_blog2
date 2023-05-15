package com.blog.controller;

import com.blog.entity.Dto.PageDto;
import com.blog.service.*;
import com.blog.util.ExceptionHandler.BlogException;
import com.blog.util.GetTokenAccountId;
import com.blog.util.result.Result;
import com.blog.util.result.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Resource
    private ListService listService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UrlService urlService;
    //获取博客和评论信息

    @ApiOperation(value = "获得博客详细页面")
    @GetMapping( "GetBlogAndComment")
    public Result GetBlogAndComment(@RequestParam(value = "blogId") int blogId, HttpServletRequest httpServletRequest
    ){
        if (blogId ==0){
            throw new BlogException(ResultCodeEnum.DATA_ERROR);
        }
        //获得token中用户ID
        String accountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        Map<String,Object> map = new HashMap<>();
        map.put("blog",blogService.QueryBlog(blogId,accountId));
        map.put("comment",commentService.queryComment(blogId,0,accountId));
        return Result.ok(map);
    }
    //主页获得所有博客
    @ApiOperation(value = "获得博客集合")
    @GetMapping(value = "GetBlogList")
    public Result GetBlogList(){
        return Result.ok(blogService.QueryBlogList());
    }

    //博客详情页获得评论数
    @ApiOperation(value = "获得评论总数")
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

    @ApiOperation("排序后博客展示")
    @PostMapping (value="getVoBolgList")
    public Result getSortBlogListVo(@RequestBody PageDto pageDto)
    {
        return Result.ok(listService.getBlogListVo(pageDto));
    }
    @ApiOperation(value = "获得用户发的博客集合")
    @GetMapping ("/getUserBlogList")
    public Result getUserBlogList(@RequestParam(value = "accountId")String accountId){
        if (accountId == null) {
            throw new BlogException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(blogService.QueryUserBlogList(accountId));
    }

    @ApiOperation("查看用户关注列表")
    @GetMapping("/getUserLikeList")
    public Result getUserLikeList(@RequestParam(value = "accountId")String accountId,HttpServletRequest httpServletRequest) {
            if (accountId == null) {
                throw new BlogException(ResultCodeEnum.DATA_ERROR);
            }
            return Result.ok(likeService.UserLikeList(httpServletRequest, accountId));


    }
    @ApiOperation("用户页面的点赞、关注、粉丝数")
    @GetMapping("/getLikeCount")
    public Result getLikeCount(@RequestParam(value = "accountId")String accountId){
        if (accountId==null){
            throw new BlogException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(likeService.getCount(accountId));

    }
    @ApiOperation("查看用户粉丝列表列表")
    @GetMapping("/getLikeUserList")
    public Result getLikeUserList(@RequestParam(value = "accountId")String accountId,HttpServletRequest httpServletRequest) {
        if (accountId == null) {
            throw new BlogException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(likeService.LikeUserList(httpServletRequest, accountId));

    }

    @ApiOperation("获得首页列表")
    @GetMapping("/getUrlList")
    public Result getUrlList(){
        return Result.ok(urlService.QueryUrl());
    }


    @ApiOperation("获得搜索页列表")
    @GetMapping("/getSearchUrlList")
    public Result getSearchUrlList(){
        return Result.ok(urlService.querySearchList());
    }

    @ApiOperation("获得消息页列表")
    @GetMapping("/getMessageUrlList")
    public Result getMessageUrlList(){
        return Result.ok(urlService.queryMessageList());
    }

    @ApiOperation("获得活动页页列表")
    @GetMapping("/getActivityUrlList")
    public Result getActivityUrlList(){
        return Result.ok(urlService.queryActivityList());
    }

}
