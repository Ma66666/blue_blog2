package com.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class GetSetRedis {
    @Autowired
    private RandomFour randomFour;


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * @param phone 短信手机号码
     * @return
     */
    public String getValue(String phone){
        return redisTemplate.opsForValue().get(phone);
    }


    /**
     *
     * @param phome 短信手机号码
     * @param code  验证码
     * @return 执行结果
     */
    public Boolean setValue(String phome,String code){
        try {
            redisTemplate.opsForValue().set(phome,code,10, TimeUnit.MINUTES);
            return true;
        }catch (Exception e){
            return false;
        }
    }



}
