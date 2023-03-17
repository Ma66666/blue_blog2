package com.blog.service;

import com.blog.entity.Vo.ApplyVo;
import com.blog.entity.Vo.FriendVo;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FriendService {
    /**
     * 查询用户好友列表
     * @param httpServletRequest
     * @return
     */
    List<FriendVo> getFriendList(HttpServletRequest httpServletRequest);

    /**
     * 查询用户申请列表
     * @param httpServletRequest
     * @return
     */
    List<ApplyVo> getApplyList(HttpServletRequest httpServletRequest);

    /**
     * 更改申请状态
     * @param httpServletRequest
     * @param user_accountId 发请好友请求的用户
     * @param status 改变的状态
     */
    void updateApplyStatus(HttpServletRequest httpServletRequest,String user_accountId,int status);


    /**
     *
     * @param httpServletRequest
     * @param user_accountId 之前发起好友申请的用户
     */
    void deleteFriend(HttpServletRequest httpServletRequest,String user_accountId);


    int queryApplyCount(HttpServletRequest httpServletRequest);
}
