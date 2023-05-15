package com.blog.dao;

import com.blog.entity.GroupChat;
import com.blog.entity.User;
import com.blog.entity.Vo.FriendVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupChatMapper {
       int insertGroupChat(GroupChat groupChat);

       int updateGroupChat(GroupChat groupChat);

       int deleteGroupChat(GroupChat groupChat);

       List<GroupChat> selectGroupChat(String accountId);

       List<GroupChat> selectGroupChat2();

       int updateCount(@Param("accountId") String accountId, @Param("chatNumber") String chatNumber ); //未读消息清0

       int updateCount1(@Param("accountId") String accountId, @Param("chatNumber") String chatNumber );

       //获得群成员
       User queryUser(String accountId);

       //查群号成员
       List<GroupChat> selectGroupChat1(String chatNumber);

       String queryUserStatus(@Param("accountId") String accountId, @Param("chatNumber") String chatNumber );

       int queryCount(@Param("accountId") String accountId, @Param("chatNumber") String chatNumber);

       //查群主
       String queryZHUren(String chatNumber);

       int updatequeren(@Param("accountId") String accountId, @Param("chatNumber") String chatNumber);

}
