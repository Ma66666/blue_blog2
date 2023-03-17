package com.blog.dao;

import com.blog.entity.Vo.ApplyVo;
import com.blog.entity.Vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendMapper {

    List<String> getFriendList(String accountId); //查看我的朋友

    List<ApplyVo> queryApplyList(String accountId);  //查找申请列表

    int updateApplyStatus(String user_accountId,String friend_accountId,int status); //更改申请状态

    int deleteFriend(String user1_user2);//删除好友

    int queryApplyCount(String friend_accountId);


}
