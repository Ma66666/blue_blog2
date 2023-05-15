package com.blog.Friend;

import com.blog.BlueBlogApplication;
import com.blog.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class SensitiveFilter1 {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void setSensitiveFilter(){
        String text = "这里可以嫖娼，喝酒，吸毒，开票";
        sensitiveFilter.filter(text);
        System.out.println(sensitiveFilter.filter(text).get("String"));
        System.out.println(sensitiveFilter.filter(text).get("num"));
    }
}
