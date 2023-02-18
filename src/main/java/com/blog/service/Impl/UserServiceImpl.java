package com.blog.service.Impl;

import com.blog.config.QiNiuYunConfig;
import com.blog.dao.UserMapper;
import com.blog.entity.User;
import com.blog.entity.Vo.UserVo;
import com.blog.entity.Vo.loginVo;
import com.blog.entity.Vo.RegisterVo;
import com.blog.service.UserService;
import com.blog.util.BlogToken;
import com.blog.util.CommunityUtil;
import com.blog.util.ExceptionHandler.BlogException;
import com.blog.util.GetSetRedis;
import com.blog.util.SendSms;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.blog.util.result.ResultCodeEnum.Header_Url_ERROR;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
   private UserMapper userMapper;

    @Autowired
    private SendSms sendSms;

    @Autowired
    private GetSetRedis getSetRedis;

    @Autowired
    private QiNiuYunConfig qiNiuYunConfig;
    @Value("${qiniu.bucket.header.url}")
    private String path;

    @Override
    public User findUserByAccountId(String accountId) {
        return userMapper.selectByAccountId(accountId,"");
    }

    @Override
    public Map<String,Object> register(RegisterVo registervo) {
        Map<String,Object> map = new HashMap<>();
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
            user.setStatus("0");
            user.setSex(registervo.getSex());
            user.setHeaderUrl(String.format("http://qn.yxwhzj6.top/moren.png"));
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

    @Override
    public void settoken(String token,loginVo loginVo) {
       User user = userMapper.selectByAccountId(loginVo.getAccountId(), loginVo.getPhone());
        UserVo userVo = new UserVo();
        userVo.setUsername(user.getUsername());
        userVo.setAccountId(user.getAccountId());
        userVo.setHeaderUrl(user.getHeaderUrl());
        userVo.setCreateTime(user.getCreateTime());
        userVo.setSex(user.getSex());
        userVo.setStatus(user.getStatus());
        userVo.setSignature(user.getPSignature());
        getSetRedis.setToken(token,userVo);
    }

    @Override
    public Map<String, Object> updateUserInfo(String token,UserVo userVo) {
        Map<String,Object> map = new HashMap<>();
        System.out.println(userVo.getHeaderUrl());
        System.out.println(userVo.getSex());
        if (userMapper.updateUserInfo(userVo) == 1){
            map.put("userMsg","修改成功");
            getSetRedis.setToken(token,userVo);

            return map;
        }
        map.put("userMsg","修改失败");
        return map;
    }

    /**
     *
     * @param headerImage 图片文件
     * @param accountId   用户ID
     * @return
     */
    @Override
    public boolean updateHeaderUrl(MultipartFile headerImage,String accountId) throws IOException {
        String filename = headerImage.getOriginalFilename();
        FileInputStream inputStream = (FileInputStream) headerImage.getInputStream();
        //为文件重命名：uuid+filename
        filename = UUID.randomUUID()+ filename;
        String link = qiNiuYunConfig.uploadImgToQiNiu(inputStream, filename);
        if (link.equals("false")){
            throw new BlogException( Header_Url_ERROR);
        }
        String url = "http://"+path+"/"+filename;
       User user = userMapper.selectByAccountId(accountId,"");
        //七牛云删除只要图片名称，所以把域名去掉
        String deleteurl = user.getHeaderUrl().replace("http://qn.yxwhzj6.top/","");
        qiNiuYunConfig.deleteFile(deleteurl);
       int i = userMapper.updateUserImg(accountId,url);
       if (i==1){
           return true;
       }
        return false;
    }

    @Override
    public UserVo queryUserInfo(String accountId) {
        User user = userMapper.selectByAccountId(accountId,"");
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return userVo;
    }


}
