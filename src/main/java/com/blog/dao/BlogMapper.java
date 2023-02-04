package com.blog.dao;

import com.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogMapper {
    Blog queryById(int id); //根据id查询帖子信息

    int insertBlog(Blog blog); //添加博客


}
