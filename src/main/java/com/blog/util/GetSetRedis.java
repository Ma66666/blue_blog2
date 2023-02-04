package com.blog.util;

import com.alibaba.fastjson.JSON;
import com.blog.entity.User;
import com.blog.entity.Vo.UserVo;
import com.tencentcloudapi.bsca.v20210811.models.PURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class GetSetRedis {

    private static final String SPLIT = ":";
    private static final String PREFIX_PHONE = "phone";
    private static final String PREFIX_TOKEN = "token";
    private static final String PREFIX_REGISTER = "register";

    public static String getPrefixPhone(String phone) {
        return PREFIX_PHONE + SPLIT + phone;
    }
    public static String getPrefixPhone1(String phone) {
        return PREFIX_PHONE + SPLIT + PREFIX_REGISTER +SPLIT +phone;
    }
    public static String getPrefixToken(String token){
        return PREFIX_TOKEN +SPLIT +token;
    }

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

//    //判断是否存在key
//    public boolean hasKey(String key) {
//        return redisTemplate.hasKey(key);
//    }
public boolean hashkey(String token){
    if ( redisTemplate.hasKey(getPrefixToken(token))){
        return true;
    }
    return false;
}

    /**
     * 注册验证码
     * @param phone
     * @return
     */
    public String getRegister(String phone){
        return redisTemplate.opsForValue().get(getPrefixPhone1(phone));
    }



    /**
     * 获取手机验证码
     * @param phone 短信手机号码
     * @return 验证码
     */
    public String getValue(String phone){
        return redisTemplate.opsForValue().get(getPrefixPhone(phone));
    }

    /**
     * 获取用户token
     * @param token 用户ID
     * @return token
     */
    public String getToken(String token){

        return redisTemplate.opsForValue().get(getPrefixToken(token));
    }



    /**
     *将验证码存入redis
     * @param phone 短信手机号码
     * @param code  验证码
     * @return 执行结果
     */
    public Boolean setValue(String phone,String code){
        try {
            redisTemplate.opsForValue().set(getPrefixPhone(phone),code,1, TimeUnit.DAYS);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     *
     * @param token 用户凭证
     * @param userVo 前端需要的实体
     */
    public void setToken(String token, UserVo userVo){
         String text= JSON.toJSONString(userVo);
         redisTemplate.opsForValue().set(getPrefixToken(token),text);

    }

    public void setRegister(String phone,String code){
        redisTemplate.opsForValue().set(getPrefixPhone1(phone),code,1,TimeUnit.DAYS);
    }


}
