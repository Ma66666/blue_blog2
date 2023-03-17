package com.blog.ChatMessage;

import com.blog.BlueBlogApplication;
import com.blog.dao.ChatListMapper;
import com.blog.dao.ChatMessageMapper;
import com.blog.dao.UserMapper;
import com.blog.entity.ChatMessage;
import com.blog.entity.User;
import com.blog.entity.Vo.ChatUserListVo;
import com.blog.entity.Vo.MessageListVo;
import com.blog.util.GetTokenAccountId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class ChatMessageTest {

    @Autowired
    private ChatListMapper chatListMapper;
    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private UserMapper userMapper;


    @Test
    public void queryUserContact(){
        System.out.println(chatListMapper.queryUserContact("Hot12345","OjNiPqde"));
        System.out.println(chatListMapper.queryUserContact("OjNiPqde","Hot12345"));
    }
    @Test
    public void queryEveryChatLatestWord(){
        System.out.println(chatMessageMapper.queryEveryChatLatestWord("Hot12345"));
    }
    @Test
    public void queryChatUserListVo(){
        String accountId = "Hot12345";
        List<ChatMessage> chatlist = chatMessageMapper.queryEveryChatLatestWord(accountId);
        List<ChatUserListVo> list = new ArrayList<>();
        for (ChatMessage chatMessage : chatlist){
            String accountId1 = accountId.equals(chatMessage.getSendAccountId())?chatMessage.getToAccountId():chatMessage.getSendAccountId();
            User user = userMapper.selectByAccountId(accountId1,"");
            ChatUserListVo chatUserListVo = new ChatUserListVo();
            chatUserListVo.setMessage(chatMessage.getMessage());
            chatUserListVo.setCreateTime(chatMessage.getCreateTime());
            chatUserListVo.setUsername(user.getUsername());
            chatUserListVo.setAccountId(accountId1);
            chatUserListVo.setHeaderUrl(user.getHeaderUrl());
            chatUserListVo.setUser1User2(chatMessage.getUser1User2());
            list.add(chatUserListVo);
        }
        System.out.println(list);
    }
    @Test
    public void queryMessageList(){
        String accountId = "Hot12345";
        List<ChatMessage> list = chatMessageMapper.queryChatMessageList("Hot12345_OjNiPqde");
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
       System.out.println(list1);
    }

    @Test
    public void quertUnread(){
        System.out.println(chatMessageMapper.queryChatUnreadCount("Hot12345"));
    }
}
