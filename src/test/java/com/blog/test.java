package com.blog;

import com.blog.entity.Blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class test {



    @Test
    public void getUser(){
//        System.out.println(userMapper.selectByAccountId("abcdefg"));
    }

//    @Test
//    public void test(){
//         User user = userMapper.selectByphone("17336122191");
//
//        System.out.println( user.getCreateTime());
//    }
//    @Test
//    public void insert(){
//        User user = new User();
//        System.out.println(userMapper.insertUser(user));
//    }

}
