package com.blog.dao.es;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EsMapper {

    List<EsVo> queryBlog();//查询所有博客（用于存储es)

    List<EsVo> queryEsBlogListByMyLikeBlog(List<Integer> list);




}
