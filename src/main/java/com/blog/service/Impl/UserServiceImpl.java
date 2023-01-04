package com.blog.service.Impl;

import com.blog.dao.UserMapper;
import com.blog.entity.User;
import com.blog.entity.Vo.loginVo;
import com.blog.entity.Vo.registerVo;
import com.blog.result.BlogException;
import com.blog.result.ResultCodeEnum;
import com.blog.service.UserService;
import com.blog.util.CommunityUtil;
import com.blog.util.GetSetRedis;
import com.blog.util.SendSms;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
   private UserMapper userMapper;

    @Autowired
    private SendSms sendSms;

    @Autowired
    private GetSetRedis getSetRedis;

    @Override
    public User findUserByAccountId(String accountId) {
        return userMapper.selectByAccountId(accountId,"");
    }

    @Override
    public Map<String,Object> register(registerVo registervo) {
        System.out.println(registervo.getPassword());
        System.out.println(registervo.getPhone());
        System.out.println(registervo.getUsername());
        Map<String,Object> map = new HashMap<>();
        if (registervo == null){
            throw new BlogException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
        if (StringUtils.isBlank(registervo.getUsername())){
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(registervo.getPassword())){
            map.put("passwordMsg","密码不能为空");
            return map;
        }

        if (StringUtils.isBlank(registervo.getPhone())){
            map.put("phoneMsg","手机号不能为空");
            return map;
        }

        //判断账号是否存在
        User user1 = userMapper.selectByphone(registervo.getPhone());
        if (user1 !=null){
            map.put("phoneMsg","该手机号已被注册");
            return map;
        }
        //注册用户
        User user = new User();
            user.setUsername(registervo.getUsername());
            user.setAccountId(CommunityUtil.generateShortUuid());
            user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
            user.setPassword(CommunityUtil.md5(registervo.getPassword()+ user.getSalt()));
            user.setPhone(registervo.getPhone());
            user.setStatus(0);
            user.setHeaderUrl(String.format("http://images.nowcoder.com/head/22t.png"));
            user.setCreateTime(new Date());
            userMapper.insertUser(user);
            map.put(user.getPhone(),"插入成功");
            return map;
    }

    @Override
    public boolean send(String phoneNum, String templateCode, String code,String code2) {

        return sendSms.send(phoneNum,templateCode,code,code2);
    }

    @Override
    public boolean login(loginVo loginuser) {
        User user = userMapper.selectByAccountId(loginuser.getAccountId(),loginuser.getPhone());
        if(user==null){
            return false;
        }

        String s = CommunityUtil.md5(loginuser.getPassword()+user.getSalt());
        if (user.getPassword().equals(s)||
                loginuser.getCode().equals(getSetRedis.getValue(loginuser.getPhone()))){
            return true;
        }

        return false;
    }

}
