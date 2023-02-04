package com.blog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blog.dao.UserMapper;
import com.blog.entity.User;
import com.blog.entity.Vo.UserVo;
import com.blog.util.GetSetRedis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlueBlogApplication.class)
public class redistest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GetSetRedis getSetRedis;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Test
    public void test(){
        User user = userMapper.selectByphone("17336122191");
        UserVo userVo = new UserVo();
        userVo.setUsername(user.getUsername());
        userVo.setAccountId(user.getAccountId());
        userVo.setHeaderUrl(user.getHeaderUrl());
        userVo.setCreateTime(user.getCreateTime());

        getSetRedis.setToken("token",userVo);
        System.out.println(redisTemplate.hasKey(""));
       System.out.println(getSetRedis.getToken("token"));
    }
    @Test
    public void gettoekn(){
        System.out.println(getSetRedis.hashkey("eyJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50SWQiOiJPak5pUHFkZSJ9.se2JaXyOsjqnNYaa7C6w6IU9ZazE5FleY_vcmwu2cX8"));
        System.out.println(redisTemplate.opsForValue().get(getSetRedis.getToken("eyJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50SWQiOiJPak5pUHFkZSJ9.se2JaXyOsjqnNYaa7C6w6IU9ZazE5FleY_vcmwu2cX8")));
    }

    @Test
    public void getobject(){
        Object object = JSONObject.parse(getSetRedis.getToken("eyJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50SWQiOiJPak5pUHFkZSJ9.se2JaXyOsjqnNYaa7C6w6IU9ZazE5FleY_vcmwu2cX8"));
          UserVo userVo = JSON.parseObject(getSetRedis.getToken("eyJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50SWQiOiJPak5pUHFkZSJ9.se2JaXyOsjqnNYaa7C6w6IU9ZazE5FleY_vcmwu2cX8"),UserVo.class);
          System.out.println(userVo.getUsername());
        System.out.println();
    }
}
