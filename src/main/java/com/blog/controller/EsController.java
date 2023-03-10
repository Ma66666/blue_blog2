package com.blog.controller;

import com.blog.service.EsService;
import com.blog.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("api/search")
@RestController
public class EsController {
   @Autowired
   private  EsService esService;

    @GetMapping("getBlogEsList")
    public Result getBlogEsList(@RequestParam(value = "page")int pagenum, @RequestParam("size") int size, HttpServletRequest httpServletRequest){

        return Result.ok(esService.queryEsBlogList(pagenum,size,httpServletRequest));

    }
    @GetMapping("getBlogEsListDate")
    public Result getBlogEsListDate(@RequestParam(value = "page")int pagenum, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        return Result.ok(esService.quertEsBlogListByDate(pagenum,size,httpServletRequest));
    }

    @GetMapping("getByMyLikeBlog")
    public Result getByMyLikeBlog(@RequestParam(value = "page")int pagenum, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        return Result.ok(esService.queryEsBlogListByMyLikeBlog(pagenum,size,httpServletRequest,1));
    }
    @GetMapping("getByMyLikeUserBlog")
    public Result getByMyLikeUserBlog(@RequestParam(value = "page")int pagenum, @RequestParam("size") int size, HttpServletRequest httpServletRequest){
        return Result.ok(esService.queryEsBlogListByUserLike(pagenum,size,httpServletRequest,1));
    }
    @GetMapping("getEsBlogListByCondition")
    public Result EsBlogListByCondition(@RequestParam(value = "page")int pagenum,
                                        @RequestParam("size") int size,
                                        HttpServletRequest httpServletRequest,
                                        @RequestParam("condition")  String condition
                                        ){
        return Result.ok(esService.queryEsBlogListByCondition(pagenum,size,httpServletRequest,condition));
    }
}
