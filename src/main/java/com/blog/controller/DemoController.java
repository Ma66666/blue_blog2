package com.blog.controller;

import com.blog.config.QiNiuYunConfig;
import com.blog.entity.dao.ListMapper;
import com.blog.entity.dao.UserMapper;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.service.LikeService;
import com.blog.util.GetTokenAccountId;
import com.blog.util.result.Result;
import com.blog.service.UserService;
import com.blog.util.SendSms;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags="demo测试controller，不被拦截器拦截")
@RestController
@RequestMapping("api/demo")
@CrossOrigin
public class DemoController {
    @Autowired
    private SendSms sendSms;
    @Autowired
    private UserService userService;
    @Autowired
    private QiNiuYunConfig qiNiuYunConfig;
    @Value("${qiniu.bucket.header.url}")
    private String path;
    @Value("${qiniu.key.access}")
    private String accessKey;
    @Value("${qiniu.key.secret}")
    private String secretKey;
    @Value("${qiniu.bucket.header.name1}")
    private String bucket;
    @Autowired
    private BlogService blogService;

    @Resource
    private GetTokenAccountId getTokenAccountId;

    @Resource
    private CommentService commentService;

    @Resource
    private LikeService likeService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ListMapper listMapper;


//    @GetMapping("123")
//    public Result ll(String a){
//
//        return Result.ok();
//    }



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
