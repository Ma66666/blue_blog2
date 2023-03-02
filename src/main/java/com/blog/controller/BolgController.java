package com.blog.controller;

import com.blog.config.QiNiuYunConfig;
import com.blog.service.BlogService;
import com.blog.util.BlogToken;
import com.blog.util.ExceptionHandler.BlogException;
import com.blog.util.GetTokenAccountId;
import com.blog.util.result.Result;
import com.qiniu.util.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.blog.util.result.ResultCodeEnum.DATA_ERROR;

@Api(tags="blog")
@RestController
@RequestMapping("api/blog")
@CrossOrigin
public class BolgController {
    @Resource
    private BlogService blogService;
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
    @Resource
    private GetTokenAccountId getTokenAccountId;

    @ApiOperation(value = "获得用户草稿")
    @GetMapping("getBlogListCg")
    private Result getBlogListCg(HttpServletRequest httpServletRequest){
        String accountId = getTokenAccountId.getTokenAccountId(httpServletRequest);
        if (accountId == null){
            throw new BlogException(DATA_ERROR);
        }
        return Result.ok(blogService.queryByListCg(accountId));
    }

    @ApiOperation(value = "用户删除博客")
    @PostMapping("deleteBlog")
    private Result deleteBlog(@RequestParam("blogId") int blogId){
        return Result.ok(blogService.deleteBlog(blogId));
    }

    @ApiOperation(value = "保存博客、或发送博客")
    //保存博客
    @PostMapping("/saveCg")
    public Result SaveBlog(@RequestParam(value = "title")String title,
                           @RequestParam(value = "content")String content,
                           @RequestParam(value = "cover")String cover,
                           @RequestParam(value = "ImgList") List<Object> ImgList,
                           @RequestParam(value = "type") int type,
                           @RequestParam(value = "topic") String topic,
                           @RequestParam(value = "id")int id,
                           HttpServletRequest httpServletRequest
    ){
        System.out.println(title);
        if (title == null||content==null ||cover ==null || ImgList.size()==0 ||topic==null){
            throw new BlogException(DATA_ERROR);
        }
        System.out.println(id);
        String token = httpServletRequest.getHeader("Authorization");
        Map<String,Object> map = BlogToken.parserJavaWebToken(token);
        String accountId = (String) map.get("accountId");
        System.out.println(type);
        blogService.saveCg(title,content,cover,ImgList,type,accountId,topic,id);
        return Result.ok();
    }



    @ApiOperation(value = "保存或发送博客")
    @PostMapping("/saveBlog")
    public Result SaveBlog(@RequestParam(value = "title")String title,
                           @RequestParam(value = "content")String content,
                           @RequestParam(value = "cover")String cover,
                           @RequestParam(value = "ImgList")List<Object> ImgList,
                           @RequestParam(value = "type") int type,
                           @RequestParam(value = "topic") String topic,
                           HttpServletRequest httpServletRequest
    ){
        System.out.println(title);
        if (title == null||content==null ||cover ==null || ImgList.size()==0 ||topic==null){
            throw new BlogException(DATA_ERROR);
        }
        String token = httpServletRequest.getHeader("Authorization");
        Map<String,Object> map = BlogToken.parserJavaWebToken(token);
        String accountId = (String) map.get("accountId");
        System.out.println(type);

        blogService.insertBlog(title,content,cover,ImgList,type,accountId,topic);
//        System.out.println(title);
//        System.out.println(content);
//        System.out.println(cover);
//        System.out.println(type);

//       System.out.println("我是blog1"+blog.getImage1());

        return Result.ok();
    }

    @ApiOperation(value = "获取七牛云token")
    @GetMapping(value = "policy")
    public Result Policy(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dir = sdf.format(new Date());
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Map<String, String> respMap = new HashMap<>();
        respMap.put("token",upToken);
        return Result.ok(respMap);
    }

    //发博客页面删除图片功能
    @ApiOperation(value = "博客页面输图片功能")
    @PostMapping ("/delectImg")
    public Result DeleteImg(@RequestParam(value = "Img")String Img){
        if (Img == null){
            throw new BlogException(DATA_ERROR);
        }
        qiNiuYunConfig.deleteFile1(Img);
        return Result.ok("删除成功");
    }

}
