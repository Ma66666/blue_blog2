package com.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogLikeMapper {
    List<Integer> queryUserLikeBlog(String accountId);
}
