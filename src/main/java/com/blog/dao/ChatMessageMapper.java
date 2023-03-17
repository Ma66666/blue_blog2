package com.blog.dao;

import com.blog.entity.Bo.MessageCountBo;
import com.blog.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    // 查询当前用户的会话列表,针对每个会话只返回一条最新的私信.
    List<ChatMessage> queryEveryChatLatestWord(String accountId);

    //查询某个会话的历史聊天记录
    List<ChatMessage> queryChatMessageList(String user1_user2);

    //插入聊天记录
    int insertChatMessage(ChatMessage chatMessage);

    //查询未读的聊天信息数量
    List<MessageCountBo> queryChatUnreadCount(String accountId);

    //点击用户会话，清除消息状态
    int updataChatMessageStatus(String user1_user2);
}
