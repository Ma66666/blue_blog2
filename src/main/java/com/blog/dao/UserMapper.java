package com.blog.dao;

import com.blog.entity.User;
import com.blog.entity.Vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface  UserMapper {
   User selectByAccountId(String accountId,String phone);//获取user信息

   User selectByphone(String phone);//获取user信息

   int insertUser(User user); //插入用户信息

   int updateUserInfo(UserVo userVo); //更换用户信息

   int updateUserImg(String accountId,String headerUrl); //更换用户头像

}
