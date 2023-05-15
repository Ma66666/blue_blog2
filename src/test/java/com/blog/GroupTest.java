package com.blog;

import com.blog.dao.GroupChatMapper;
import com.blog.dao.GroupMessageMapper;
import com.blog.dao.UserMapper;
import com.blog.entity.GroupChat;
import com.blog.entity.GroupMessage;
import com.blog.entity.User;
import com.blog.entity.Vo.GroupListVo;
import com.blog.service.GroupService;
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
public class GroupTest {
    @Autowired
    private GroupChatMapper chatMapper;

    @Autowired
    private GroupMessageMapper groupMessageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupService groupService;
    @Test
    public void queryGroupList(){
        System.out.println(chatMapper.selectGroupChat("hot12345"));
    }

    @Test
    public void delete(){
System.out.println(chatMapper.queryCount("12345","xsb123415")==0);
        groupService.deleteUser("12345","xsb12345");
    }

    @Test
    public void queryGroupList1(){
        List<GroupChat> list = chatMapper.selectGroupChat("Hot12345");
        List<GroupListVo> listVo = new ArrayList<>();
        for (GroupChat groupChat :list){
            GroupListVo groupListVo = new GroupListVo();
            groupListVo.setCreateTime(groupChat.getCreateTime());
            groupListVo.setChatNumber(groupChat.getChatNumber());
            groupListVo.setHeaderUrl(groupChat.getHeaderUrl());
            groupListVo.setName(groupChat.getName());
            if (groupChat.getNum()>0) {
                groupListVo.setCount(groupChat.getNum()+"");
            }
            GroupMessage groupMessage =  groupMessageMapper.queryGroupList(groupChat.getChatNumber());
            groupListVo.setCreateTime(groupMessage.getCreateTime());
            groupListVo.setMessage(groupMessage.getMessage());
            User user = userMapper.selectByAccountId(groupMessage.getAccountId(),"");
            groupListVo.setUsername(user.getUsername());
            listVo.add(groupListVo);
        }
        System.out.println(listVo);
    }
}
