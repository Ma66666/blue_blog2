package com.blog;


import com.blog.dao.FriendMapper;
import com.blog.dao.ListMapper;
import com.blog.dao.UserMapper;
import com.blog.entity.Dto.PageDto;
import com.blog.entity.Vo.FriendVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class BlogTest {
    @Autowired
    private ListMapper listMapper;

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
        }
        System.out.println(userVos);
    }

    @Test
    public void queryBlogListByHot1(){
      PageDto pageDto =  new PageDto();

      pageDto.setStart(0);
      pageDto.setTotal(5);
        System.out.println(listMapper.queryBlogListByHot1(pageDto));

    }
    @Test
    public void queryBlogListByHot(){
        PageDto pageDto = new PageDto();

        System.out.println(listMapper.queryBlogListByHot(pageDto));
        System.out.println(listMapper.queryHotCount(pageDto));
    }
    @Test
    public void queryBlogListByMonthAndHot() throws ParseException {

        PageDto pageDto = new PageDto();
        pageDto.setCondition("健身");
        listMapper.queryBlogListByMonthAndHot(pageDto);
        System.out.println(listMapper.queryMonthCount(pageDto));
        pageDto.setStart(0);
        pageDto.setTotal(1);
        listMapper.queryBlogListByMonthAndHot1(pageDto);
    }

    @Test
    public void queryBlogListByWeekAndHot(){
        PageDto pageDto = new PageDto();
      System.out.println(listMapper.queryWeekCount(pageDto));
        pageDto.setCondition("健身");
        listMapper.queryBlogListByWeekAndHot(pageDto);
        System.out.println(listMapper.queryWeekCount(pageDto));
        pageDto.setStart(0);
        pageDto.setTotal(2);
        listMapper.queryBlogListByWeekAndHot1(pageDto);
    }

    @Test
    public void queryBlogListByDayAndHot(){
        PageDto pageDto = new PageDto();
        System.out.println(listMapper.queryDayCount(pageDto));
        pageDto.setCondition("健身");
        System.out.println(listMapper.queryDayCount(pageDto));
        listMapper.queryBlogListByDayAndHot(pageDto);
        pageDto.setStart(0);
        pageDto.setTotal(2);
        listMapper.queryBlogListByDayAndHot1(pageDto);
    }

    @Test
    public void all(){

    }

    @Test
    public void all1(){

    }
}
