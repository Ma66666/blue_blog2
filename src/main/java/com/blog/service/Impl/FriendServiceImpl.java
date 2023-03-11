package com.blog.service.Impl;

import com.blog.entity.dao.FriendMapper;
import com.blog.entity.dao.UserMapper;
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
}
