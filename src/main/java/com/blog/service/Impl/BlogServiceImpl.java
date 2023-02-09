package com.blog.service.Impl;

import com.blog.dao.BlogMapper;
import com.blog.entity.Blog;
import com.blog.entity.Vo.BlogListVo;
import com.blog.entity.Vo.BlogVo;
import com.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        BlogVo blogVo = blogMapper.queryById(blogId);
            blogVo.list.add(blogVo.getImage1());
        blogVo.list.add(blogVo.getImage2());
        blogVo.list.add(blogVo.getImage3());
        blogVo.list.add(blogVo.getImage4());
        blogVo.list.add(blogVo.getImage5());
        blogVo.list.add(blogVo.getImage6());
        blogVo.list.add(blogVo.getImage7());
        blogVo.list.add(blogVo.getImage8());
        blogVo.list.add(blogVo.getImage9());
        blogVo.list.removeAll(Collections.singleton(null));
        return blogVo;
    }

    @Override
    public List<BlogListVo> QueryBlogList() {

        return blogMapper.queryByList();
    }
}
