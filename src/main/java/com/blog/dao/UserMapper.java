package com.blog.dao;

import com.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface  UserMapper {
   User selectByAccountId(String accountId);
}
