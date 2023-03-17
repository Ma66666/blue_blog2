package com.blog.service.Impl;

import com.blog.dao.ChatListMapper;
import com.blog.dao.FriendMapper;
import com.blog.dao.UserMapper;
import com.blog.entity.Vo.ApplyVo;
import com.blog.entity.Vo.FriendVo;
import com.blog.service.FriendService;
import com.blog.util.ExceptionHandler.BlogException;
import com.blog.util.GetTokenAccountId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.blog.util.result.ResultCodeEnum.SIGN_ERROR;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChatListMapper chatListMapper;
    @Override
    public List<FriendVo> getFriendList(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        if (accountId.equals("空的")){
            throw new BlogException(SIGN_ERROR);
        }
        List<String> list = friendMapper.getFriendList(accountId);
        List<FriendVo> userVos = new ArrayList<>();
        for (String str : list){
            userVos.add(userMapper.getUserInfo(str));
        }
        return userVos;
    }

    @Override
    public List<ApplyVo> getApplyList(HttpServletRequest httpServletRequest) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        if (accountId.equals("空的")){
            throw new BlogException(SIGN_ERROR);
        }
        List<ApplyVo> friendVos = friendMapper.queryApplyList(accountId);
        return friendVos;
    }

    @Override
    public void updateApplyStatus(HttpServletRequest httpServletRequest, String user_accountId, int status) {
        String friend_accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        friendMapper.updateApplyStatus(user_accountId,friend_accountId,status);
    }

    @Override
    public void deleteFriend(HttpServletRequest httpServletRequest, String user_accountId) {
        String accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        String user1_user2 = chatListMapper.queryUserContact(accountId,user_accountId);
        friendMapper.deleteFriend(user1_user2);

    }

    @Override
    public int queryApplyCount(HttpServletRequest httpServletRequest) {
        String friend_accountId = GetTokenAccountId.getTokenAccountId(httpServletRequest);
        return friendMapper.queryApplyCount(friend_accountId);
    }
}
