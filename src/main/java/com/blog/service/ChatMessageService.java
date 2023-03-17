package com.blog.service;


import com.blog.entity.Vo.ChatUserListVo;
import com.blog.entity.Vo.MessageListVo;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

public interface ChatMessageService {
    /**
     * 获取用户聊天列表
     * @param httpServletRequest
     * @return
     */
    List<ChatUserListVo> queryChatUserListVo(HttpServletRequest httpServletRequest);

    /**
     *  根据用户连接查询聊天内容
     * @param httpServletRequest
     * @param user1_user2 用户连接
     * @return
     */
    List<MessageListVo> queryMessageList(HttpServletRequest httpServletRequest,String user1_user2);

    String insertMessage(HttpServletRequest httpServletRequest,String toAccountId,String message);
}
