package com.blog.service;

import com.blog.entity.Vo.FriendVo;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FriendService {
    List<FriendVo> getFriendList(HttpServletRequest httpServletRequest);
}
