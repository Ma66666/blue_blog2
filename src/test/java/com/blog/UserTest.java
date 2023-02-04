package com.blog;

import com.blog.dao.UserMapper;
import com.blog.entity.Vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class UserTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void updateUserInfo(){
        UserVo userVo = new UserVo();
        userVo.setAccountId("OjNiPqde");
        userVo.setSex("2");
        userVo.setUsername("我是大狗");
        userVo.setSignature("不做第一");
        userVo.setStatus("0");
        if (userMapper.updateUserInfo(userVo) == 1) {
            System.out.println("返回值" + true);
        }
    }
}
