package com.blog.service;

import com.blog.entity.GroupChat;
import com.blog.entity.Vo.FriendVo;
import com.blog.entity.Vo.GroupListVo;
import com.blog.entity.Vo.MessageListVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface GroupService {
    List<GroupListVo> queryGroupList(HttpServletRequest httpServletRequest);

    Map<String,Object> queryMessageList(HttpServletRequest httpServletRequest, String chatNumber);

    //插入用户聊天信息
    String insertMessage(HttpServletRequest httpServletRequest,String chatNumber,String message);

    List<FriendVo> queryUser(HttpServletRequest httpServletRequest, String chatNumber);

    int deleteUser(String chatNumber,String accountId);

    int insertUser(String Number,HttpServletRequest httpServletRequest,String name,String headerUrl);

//查看宿友群聊
    List<GroupChat> queryGroupList1();

    int queren(String sendAccountId,int id,String chatNumber);
}
