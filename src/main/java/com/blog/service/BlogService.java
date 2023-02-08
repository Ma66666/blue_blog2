package com.blog.service;

import com.blog.entity.Dto.BlogDto;
import com.blog.entity.Vo.BlogVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BlogService {

    int insertBlog(String title, String content, String cover, List<Object> ImgList, int type,String accountId); //插入博客

    BlogVo QueryBlog(int blogId);


}
