package com.blog.Activity;

import com.blog.BlueBlogApplication;
import com.blog.dao.ActivityMapper;

import com.blog.entity.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class Activity1 {

    @Autowired
    private ActivityMapper activityMapper;
    @Test
    public void query(){
     Activity activity =  activityMapper.queryActivityById(2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(activity.getActivityendTime());
        calendar.add(Calendar.DATE, 1);
        Date newDate = calendar.getTime();
        System.out.println(newDate);
        Date date = new Date();
        if (date.before(newDate)){
            System.out.println("可以报名");
        }

        System.out.println(activityMapper.queryActivityById(2));
    }
}
