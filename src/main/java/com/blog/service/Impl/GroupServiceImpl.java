package com.blog.service.Impl;


import com.blog.dao.ChatMessageMapper;
import com.blog.dao.GroupChatMapper;
import com.blog.dao.GroupMessageMapper;
import com.blog.dao.UserMapper;
import com.blog.entity.ChatMessage;
import com.blog.entity.GroupChat;
import com.blog.entity.GroupMessage;
import com.blog.entity.User;
import com.blog.entity.Vo.FriendVo;
import com.blog.entity.Vo.GroupListVo;
import com.blog.entity.Vo.MessageListVo;
import com.blog.service.GroupService;
import com.blog.util.GetTokenAccountId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMessageMapper groupMessageMapper;

    @Autowired
    private GroupChatMapper chatMapper;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Override
    public List<GroupListVo> queryGroupList(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
       List<GroupChat> list = chatMapper.selectGroupChat(accountId);
       List<GroupListVo> listVo = new ArrayList<>();
       for (GroupChat groupChat :list){
           GroupListVo groupListVo = new GroupListVo();
           groupListVo.setId(groupChat.getId());
           groupListVo.setCreateTime(groupChat.getCreateTime());
           groupListVo.setChatNumber(groupChat.getChatNumber());
           groupListVo.setHeaderUrl(groupChat.getHeaderUrl());
           groupListVo.setName(groupChat.getName());
           groupListVo.setStatus(groupChat.getStatus());
           if (groupChat.getNum()>0) {
               groupListVo.setCount(groupChat.getNum()+"");
           }
         GroupMessage groupMessage =  groupMessageMapper.queryGroupList(groupChat.getChatNumber());
         groupListVo.setCreateTime(groupMessage.getCreateTime());
         groupListVo.setMessage(groupMessage.getMessage());
           User user = userMapper.selectByAccountId(groupMessage.getAccountId(),"");
           groupListVo.setUsername(user.getUsername());
           listVo.add(groupListVo);
       }

        return listVo;
    }

    @Override
    public Map<String,Object> queryMessageList(HttpServletRequest httpServletRequest, String chatNumber) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        List<GroupMessage> list = groupMessageMapper.queryChatMessageList(chatNumber);
            chatMapper.updateCount(accountId,chatNumber);
        List<MessageListVo> list1 = new ArrayList<>();
        for (GroupMessage groupMessage : list){
            User user = userMapper.selectByAccountId(groupMessage.getAccountId(),"");
            MessageListVo messageListVo = new MessageListVo();
            if (accountId.equals(groupMessage.getAccountId())) {
                messageListVo.setPosition("right");
                messageListVo.setAccountId(accountId);
            } else {
                messageListVo.setPosition("left");
                messageListVo.setAccountId(groupMessage.getAccountId());
            }
            messageListVo.setId(groupMessage.getId());
            messageListVo.setMessage(groupMessage.getMessage());
            messageListVo.setCreateTime(groupMessage.getCreateTime());
            messageListVo.setUsername(user.getUsername());
            messageListVo.setHeaderUrl(user.getHeaderUrl());
            list1.add(messageListVo);
        }
        String status = chatMapper.queryUserStatus(accountId,chatNumber);
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("data",list1);
        return map;

    }

    @Override
    public String insertMessage(HttpServletRequest httpServletRequest, String chatNumber, String message) {
        String accountId= GetTokenAccountId.getTokenAccountId(httpServletRequest);
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setMessage(message);
        groupMessage.setAccountId(accountId);
        groupMessage.setCreateTime(new Date());
        groupMessage.setChatNumber(chatNumber);
        chatMapper.updateCount1(accountId,chatNumber);
        if (groupMessageMapper.insertChatMessage(groupMessage) == 1){
            return "成功";
        }
        return "失败";

    }

    @Override
    public List<FriendVo> queryUser(HttpServletRequest httpServletRequest, String chatNumber) {
        String accountId= GetTokenAccountId.getTokenAccountId(httpServletRequest);
        List<GroupChat> list = chatMapper.selectGroupChat1(chatNumber);
        List<FriendVo> friendVos = new ArrayList<>();
        for (GroupChat groupChat :list){
     User user =   chatMapper.queryUser(groupChat.getAccountId());
     FriendVo friendVo = new FriendVo();
     friendVo.setAccountId(user.getAccountId());
     friendVo.setUsername(user.getUsername());
     friendVo.setHeaderUrl(user.getHeaderUrl());
     friendVos.add(friendVo);
        }
        return friendVos;
    }

    @Override
    public int deleteUser(String chatNumber, String accountId) {
        GroupChat groupChat = new GroupChat();
        groupChat.setChatNumber(chatNumber);
        groupChat.setAccountId(accountId);
        groupChat.setStatus(3);
        chatMapper.deleteGroupChat(groupChat);
        return 0;
    }

    @Override
    public int insertUser(String Number, HttpServletRequest httpServletRequest, String name, String headerUrl) {

        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);

        if (chatMapper.queryCount(accountId,Number)==1){
           return 0;
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(4);
        chatMessage.setSendAccountId(accountId);
        chatMessage.setMessage("用户:"+accountId+"提交了进: "+name+" 群申请");
        chatMessage.setToAccountId(chatMapper.queryZHUren(Number));
        chatMessage.setCreateTime(new Date());
        chatMessage.setUser1User2(Number);
        chatMessageMapper.insertNotice(chatMessage);
        GroupChat groupChat = new GroupChat();
        groupChat.setCreateTime(new Date());
        groupChat.setHeaderUrl(headerUrl);
        groupChat.setAccountId(accountId);
        groupChat.setStatus(3);
        groupChat.setDeleteType(1);
        groupChat.setName(name);
        groupChat.setChatNumber(Number);
        chatMapper.insertGroupChat(groupChat);
        return 1;
    }

    @Override
    public List<GroupChat> queryGroupList1() {
        return chatMapper.selectGroupChat2();
    }

    @Override
    public int queren(String sendAccountId, int id, String chatNumber) {

        chatMessageMapper.updataSystem(id);
        chatMapper.updatequeren(sendAccountId,chatNumber);
        return 0;
    }
}
