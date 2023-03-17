package com.blog.BlogLikeTest;

import com.blog.BlueBlogApplication;
import com.blog.dao.BlogLikeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class BlogLikeTest {

    @Autowired
    private BlogLikeMapper blogLikeMapper;
    @Test
    public void getBlogIdList(){
        System.out.println(blogLikeMapper.queryUserLikeBlog("l5gnkIZw"));
    }
}
