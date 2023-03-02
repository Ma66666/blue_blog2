package com.blog.dao;

import com.blog.entity.Vo.LikeVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    LikeVo getUserLikeVo(String accountId); //获得用户关注的用户信息
}
