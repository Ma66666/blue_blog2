package com.blog.Friend;

import com.blog.BlueBlogApplication;
import com.blog.dao.FriendMapper;
import com.blog.dao.UserMapper;
import com.blog.entity.Vo.ApplyVo;
import com.blog.entity.Vo.FriendVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class test {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendMapper friendMapper;


    @Test
    public void getFriendList(){
        List<String> list = friendMapper.getFriendList("Hot12345");
        List<FriendVo> userVos = new ArrayList<>();
        for (String str : list){
            userVos.add(userMapper.getUserInfo(str));
            System.out.println(userMapper.getUserInfo(str).getAccountId());
        }
        System.out.println(userVos);
    }
    @Test
    public void getApplyList(){
        List<ApplyVo> applyVos = friendMapper.queryApplyList("Hot12345");
        System.out.println(applyVos);
    }

    @Test
    public void updateApplyStatus(){
        friendMapper.updateApplyStatus("YSKM2288","Hot12345",1);
    }

}
