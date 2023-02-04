package com.blog;

import com.alibaba.fastjson.JSONObject;
import com.blog.dao.UserMapper;
import com.blog.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class test {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUser(){
//        System.out.println(userMapper.selectByAccountId("abcdefg"));
    }

    @Test
    public void test(){
         User user = userMapper.selectByphone("17336122191");

        System.out.println( user.getCreateTime());
    }
    @Test
    public void insert(){
        User user = new User();
        System.out.println(userMapper.insertUser(user));
    }

}
