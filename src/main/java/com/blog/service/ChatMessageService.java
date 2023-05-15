package com.blog.service;


import com.blog.entity.Vo.ChatUserListVo;
import com.blog.entity.Vo.MessageListVo;
import com.blog.entity.Vo.NoticeVo;

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

    //插入用户聊天信息
    String insertMessage(HttpServletRequest httpServletRequest,String toAccountId,String message);

    //查询赞和收藏通知
    List<NoticeVo> queryZanNotice(HttpServletRequest httpServletRequest);

    //查询评论通知
    List<NoticeVo> queryCommentNotice(HttpServletRequest httpServletRequest);

    //查询关注信息
    List<NoticeVo> queryLikeNotice(HttpServletRequest httpServletRequest);

    //查询关注信息
    List<NoticeVo> querySystemNotice(HttpServletRequest httpServletRequest);
}
