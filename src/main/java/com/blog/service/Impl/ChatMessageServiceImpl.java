package com.blog.service.Impl;

import com.blog.dao.BlogMapper;
import com.blog.dao.ChatListMapper;
import com.blog.dao.ChatMessageMapper;
import com.blog.dao.UserMapper;
import com.blog.entity.Bo.MessageCountBo;
import com.blog.entity.ChatMessage;
import com.blog.entity.User;
import com.blog.entity.Vo.ChatUserListVo;
import com.blog.entity.Vo.MessageListVo;
import com.blog.entity.Vo.NoticeVo;
import com.blog.service.ChatMessageService;
import com.blog.util.GetTokenAccountId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    private ChatListMapper chatListMapper;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<ChatUserListVo> queryChatUserListVo(HttpServletRequest httpServletRequest) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        List<ChatMessage> chatlist = chatMessageMapper.queryEveryChatLatestWord(accountId);
        List<ChatUserListVo> list = new ArrayList<>();
        List<MessageCountBo> countBo = chatMessageMapper.queryChatUnreadCount(accountId);
        for (ChatMessage chatMessage : chatlist){
            String accountId1 = accountId.equals(chatMessage.getSendAccountId())?chatMessage.getToAccountId():chatMessage.getSendAccountId();
            User user = userMapper.selectByAccountId(accountId1,"");
            ChatUserListVo chatUserListVo = new ChatUserListVo();
            chatUserListVo.setId(chatMessage.getId());
            chatUserListVo.setMessage(chatMessage.getMessage());
            chatUserListVo.setCreateTime(chatMessage.getCreateTime());
            chatUserListVo.setUsername(user.getUsername());
            chatUserListVo.setAccountId(accountId1);
            chatUserListVo.setHeaderUrl(user.getHeaderUrl());
            chatUserListVo.setUser1User2(chatMessage.getUser1User2());
            list.add(chatUserListVo);
        }
        if (countBo!=null) {
            for (ChatUserListVo chatUserListVo : list) {
                for (MessageCountBo messageCountBo : countBo) {
                    if (messageCountBo.getUser1User2().equals(chatUserListVo.getUser1User2())) {
                        chatUserListVo.setCount(messageCountBo.getCount1() + "");
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<MessageListVo> queryMessageList(HttpServletRequest httpServletRequest, String user1_user2) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        List<ChatMessage> list = chatMessageMapper.queryChatMessageList(user1_user2);

        ChatMessage chatMessage1 = list.get(list.size()-1);
        if (chatMessage1.getToAccountId().equals(accountId)){
            chatMessageMapper.updataChatMessageStatus(user1_user2);
        }

        List<MessageListVo> list1 = new ArrayList<>();
        for (ChatMessage chatMessage : list){
            User user = userMapper.selectByAccountId(chatMessage.getSendAccountId(),"");
            MessageListVo messageListVo = new MessageListVo();
            if (accountId.equals(chatMessage.getSendAccountId())) {
                messageListVo.setPosition("right");
                messageListVo.setAccountId(chatMessage.getSendAccountId());

            } else {
                messageListVo.setPosition("left");
                messageListVo.setAccountId(chatMessage.getSendAccountId());

            }
            messageListVo.setId(chatMessage.getId());
            messageListVo.setMessage(chatMessage.getMessage());
            messageListVo.setCreateTime(chatMessage.getCreateTime());
            messageListVo.setUsername(user.getUsername());
            messageListVo.setHeaderUrl(user.getHeaderUrl());
            list1.add(messageListVo);
        }

        return list1;
    }

    @Override
    public String insertMessage(HttpServletRequest httpServletRequest, String toAccountId,String message) {
        String sendAccountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        String user1_user2  = chatListMapper.queryUserContact(sendAccountId,toAccountId);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setCreateTime(new Date());
        chatMessage.setSendAccountId(sendAccountId);
        chatMessage.setToAccountId(toAccountId);
        chatMessage.setStatus(0);
        chatMessage.setUser1User2(user1_user2);
        chatMessage.setType(0);
       if (chatMessageMapper.insertChatMessage(chatMessage) == 1){
           return "成功";
       }
        return "失败";
    }

    @Override
    public List<NoticeVo> queryZanNotice(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        List<NoticeVo> list= chatMessageMapper.queryZanNoticeVo(accountId);

        return list;
    }

    @Override
    public List<NoticeVo> queryCommentNotice(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        List<NoticeVo> list= chatMessageMapper.queryCommentNoticeVo(accountId);
        return list;
    }

    @Override
    public List<NoticeVo> queryLikeNotice(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        List<NoticeVo> list= chatMessageMapper.queryLikeNoticeVo(accountId);
        return list;
    }

    @Override
    public List<NoticeVo> querySystemNotice(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        List<NoticeVo> list= chatMessageMapper.querySystemNoticeVo(accountId);
        return list;
    }
}
