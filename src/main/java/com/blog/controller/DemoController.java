package com.blog.controller;

import com.blog.config.QiNiuYunConfig;
import com.blog.dao.UserMapper;
import com.blog.entity.Blog;
import com.blog.entity.Dto.BlogDto;
import com.blog.entity.User;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.util.BlogToken;
import com.blog.util.ExceptionHandler.BlogException;
import com.blog.util.result.Result;
import com.blog.service.UserService;
import com.blog.util.SendSms;
import com.qiniu.util.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.blog.util.result.ResultCodeEnum.DATA_ERROR;
import static com.blog.util.result.ResultCodeEnum.Header_Url_ERROR;

@Api(tags="demo")
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
    private CommentService commentService;
    


    @Autowired
    private UserMapper userMapper;

    @PostMapping("/Send")
    public Boolean sendCode(@RequestParam(value = "phone",required = true) String phone) {
        return sendSms.send(phone," ","1234","1");
    }
    @ApiOperation(value = "更换头像")
    @PostMapping("/upload")
    public Result uploadHeader(@RequestParam("file") MultipartFile headerImage, HttpServletRequest request) throws IOException {
        if (headerImage == null) {
            throw new BlogException(DATA_ERROR);
        }
        String token = request.getHeader("Authorization");
        Map<String,Object> map = BlogToken.parserJavaWebToken(token);
        String accountId = (String) map.get("accountId");
        if (userService.updateHeaderUrl(headerImage,accountId)){
            return Result.ok("更换成功");
        }        return Result.fail("更换失败");
    }

//    @ApiOperation(value = "更换头象正确")
//    @PostMapping(value = "uploadImg")
//    public Result uploadImg(@RequestBody HeaderUrlDto headerUrlDto) throws IOException{
//        if (headerUrlDto.getFileName()==null||headerUrlDto.getInputStream1()==null){
//            throw new BlogException(Header_Url_ERROR);
//        }
//        String link = qiNiuYunConfig.uploadImgToQiNiu1(headerUrlDto.getInputStream1(),headerUrlDto.getFileName());
//        if (link.equals("false")){
//            return Result.fail("更换失败，请使用jpg或png格式");
//        }
//        userService.updateHeaderUrl(headerUrlDto.getAccountId());

//        if (userVo.getHeaderUrl().equals(user.getHeaderUrl())){
//            System.out.println("我错啦");
//            throw new BlogException(Header_Url_ERROR);
//        }
//        FileInputStream inputStream = (FileInputStream) headerImage.getInputStream();
//        //为文件重命名：uuid+filename
//        filename = UUID.randomUUID() + filename;
////        String link = qiNiuYunConfig.uploadImgToQiNiu(inputStream, filename);

//        return Result.ok("更换成功");

   // }
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

    @PostMapping ("/delectImg")
    public Result DeleteImg(@RequestParam(value = "Img")String Img){
        qiNiuYunConfig.deleteFile1(Img);
        return Result.ok("删除成功");
    }

    @PostMapping("/saveBlog")
    public Result SaveBlog(@RequestParam(value = "title")String title,
                           @RequestParam(value = "content")String content,
                           @RequestParam(value = "cover")String cover,
                           @RequestParam(value = "ImgList")List<Object> ImgList,
                           @RequestParam(value = "type") int type,
                             HttpServletRequest httpServletRequest
                           ){
        String token = httpServletRequest.getHeader("Authorization");
        Map<String,Object> map = BlogToken.parserJavaWebToken(token);
        String accountId = (String) map.get("accountId");
        blogService.insertBlog(title,content,cover,ImgList,type,accountId);
//        System.out.println(title);
//        System.out.println(content);
//        System.out.println(cover);
//        System.out.println(type);

//       System.out.println("我是blog1"+blog.getImage1());

        return Result.ok();
    }

    @GetMapping(value = "GetBlogAndComment")
    public Result GetBlogAndComment(int blogId){
        Map<String,Object> map = new HashMap<>();
        map.put("博客内容",blogService.QueryBlog(blogId));
        map.put("评论区",commentService.queryComment(blogId,0));
return Result.ok(map);
    }

}
