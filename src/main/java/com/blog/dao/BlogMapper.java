package com.blog.dao;

import com.blog.entity.Blog;
import com.blog.entity.Vo.BlogListVo;
import com.blog.entity.Vo.BlogVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {
    BlogVo queryById(int id); //根据id查询帖子信息

    int insertBlog(Blog blog); //添加博客

    List<BlogListVo> queryByList();//查询所有博客封面


}
