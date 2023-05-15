package com.blog.dao;

import com.blog.entity.ChatMessage;
import com.blog.entity.GroupMessage;
import com.blog.entity.Vo.GroupListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMessageMapper {

   GroupMessage queryGroupList(String chatNumber);

   //查询某个会话的历史聊天记录
   List<GroupMessage> queryChatMessageList(String chatNumber);

   //插入聊天记录
   int insertChatMessage(GroupMessage groupMessage);


}
