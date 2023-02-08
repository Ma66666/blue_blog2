package com.blog.dao;

import com.blog.entity.Blog;
import com.blog.entity.Vo.BlogVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogMapper {
    BlogVo queryById(int id); //根据id查询帖子信息

    int insertBlog(Blog blog); //添加博客


}
