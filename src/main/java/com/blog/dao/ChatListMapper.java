package com.blog.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatListMapper {

    String queryUserContact(String user1,String user2);
}
