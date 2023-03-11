package com.blog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blog.entity.dao.LikeMapper;
import com.blog.entity.dao.UserMapper;
import com.blog.entity.User;
import com.blog.entity.Vo.LikeVo;
import com.blog.entity.Vo.UserVo;
import com.blog.service.LikeService;
import com.blog.util.GetSetRedis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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
    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeMapper likeMapper;

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

    @Test
    public void setUserLike(){
      likeService.follow("OjNiPqde","YSKM2287");
    }

    @Test
    public void deleteUserLike(){
        likeService.unfollow("OjNiPqde","YSKM2287");
    }

    @Test
    public void getFollowList(){
        String meaccountId = "Hot12345";
        List<LikeVo> list  = new ArrayList<>();
        String user = GetSetRedis.getUserLike("OjNiPqde");
        Set<String> set =redisTemplate.opsForSet().members(user);
        Iterator<String> iterator  = set.iterator();
        if (meaccountId == "OjNiPqde"){
            while (iterator.hasNext()){
                LikeVo likeVo = likeMapper.getUserLikeVo(iterator.next());
                likeVo.setLiketype(1);
                list.add(likeVo);
            }
        }else {
            //求交集
            String user1 = GetSetRedis.getUserLike(meaccountId);
            Set<String> set1 = redisTemplate.opsForSet().intersect(user1,user);
            Iterator<String> iterator1  = set1.iterator();
            while (iterator1.hasNext()){
                LikeVo likeVo = likeMapper.getUserLikeVo(iterator1.next());
                likeVo.setLiketype(1);
                list.add(likeVo);
            }
            //求差集
            Set<String> set2 = redisTemplate.opsForSet().difference(user,user1);
            Iterator<String> iterator2  = set2.iterator();
            while (iterator2.hasNext()){
                LikeVo likeVo = likeMapper.getUserLikeVo(iterator2.next());
                likeVo.setLiketype(0);
                list.add(likeVo);
            }
        }
        Map<String,Object> map =new HashMap<>();
        map.put("total",set.size());
        map.put("ListVo",list);
        System.out.println(map);

    }
}
