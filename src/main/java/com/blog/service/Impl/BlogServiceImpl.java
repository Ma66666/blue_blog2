package com.blog.service.Impl;

import com.blog.dao.BlogMapper;
import com.blog.entity.Blog;
import com.blog.entity.Vo.BlogVo;
import com.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public int insertBlog(String title, String content, String cover, List<Object> ImgList, int type, String accountId) {
        Blog blog = new Blog();
        blog.setImageall(ImgList);
        blog.setAccountId(accountId);
        blog.setContent(content);
        blog.setCover(cover);
        blog.setHot(0);
        blog.setCreateTime(new Date());
        blog.setTitle(title);
        return blogMapper.insertBlog(blog);
    }

    @Override
    public BlogVo QueryBlog(int blogId) {
        return blogMapper.queryById(blogId);
    }
}
