package com.blog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.blog.config.QiNiuYunConfig;
import com.blog.dao.UserMapper;
import com.blog.entity.User;
import com.blog.entity.Vo.UserVo;
import com.blog.entity.Vo.loginVo;
import com.blog.entity.Vo.RegisterVo;
import com.blog.service.UserService;
import com.blog.util.*;
import com.blog.util.ExceptionHandler.BlogException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.blog.util.result.ResultCodeEnum.*;

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

    @Autowired
    private GetTokenAccountId getTokenAccountId;

    /**
     * 根据ID查询用户
     * @param accountId 用户ID
     * @return 返回用户实体
     */
    @Override
    public User findUserByAccountId(String accountId) {
        return userMapper.selectByAccountId(accountId,"");
    }

    /**
     * 用户注册方法
     * @param registervo 注册Vo
     * @return
     */
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

    /**
     * 用户发送短信方法
     * @param phoneNum 电话号码
     * @param templateCode 发送短信的模板
     * @param code 选中code1
     * @param code2 选中code2
     * @return 返回短信是否发送成功
     */
    @Override
    public boolean send(String phoneNum, String templateCode, String code,String code2) {
        return sendSms.send(phoneNum,templateCode,code,code2);
    }

    /**
     * 用户登录方法
     * @param loginuser 用户登录Vo
     * @return
     */
    @Override
    public  Map<String,Object> login(loginVo loginuser) {
        Map<String,Object> m = new HashMap<String,Object>();
        User user = userMapper.selectByAccountId(loginuser.getAccountId(),loginuser.getPhone());
        String token = null;
        if(user==null){
         throw new BlogException(LOGIN_MOBLE_ERROR);
        }
        String s = CommunityUtil.md5(loginuser.getPassword()+user.getSalt());
        if (user.getPassword().equals(s)||
                loginuser.getCode().equals(getSetRedis.getValue(loginuser.getPhone()))){
            m.put("accountId", user.getAccountId());
            token = BlogToken.createJavaWebToken(m);
           UserVo userVo =new UserVo();
            userVo.setUsername(user.getUsername());
            userVo.setAccountId(user.getAccountId());
            userVo.setHeaderUrl(user.getHeaderUrl());
            userVo.setCreateTime(user.getCreateTime());
            userVo.setSex(user.getSex());
            userVo.setStatus(user.getStatus());
            userVo.setPSignature(user.getPSignature());
            getSetRedis.setToken(token,userVo);
            Map<String,Object>map = new HashMap<>();
            map.put("token",token);
            map.put("UserVo",userVo);
            return map;
        }
        throw new BlogException(DATA_ERROR);
    }

    /**
     * 将token为Key，用户Vo为value存入数据库
     * @param token 工具类返回的token，包含用户信息
     * @param loginVo 用户Vo
     */
    @Override
    public void settoken(String token,loginVo loginVo) {
        System.out.println(loginVo.toString());
       User user = userMapper.selectByAccountId(loginVo.getAccountId(), loginVo.getPhone());
       System.out.println(user);
        UserVo userVo = new UserVo();
        userVo.setUsername(user.getUsername());
        userVo.setAccountId(user.getAccountId());
        userVo.setHeaderUrl(user.getHeaderUrl());
        userVo.setCreateTime(user.getCreateTime());
        userVo.setSex(user.getSex());
        userVo.setStatus(user.getStatus());
        userVo.setPSignature(user.getPSignature());
        getSetRedis.setToken(token,userVo);
    }

    /**
     * 更新用户信息
     * @param token 用户token
     * @param userVo 用户实体
     * @return
     */
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
     *更换头像
     * @param headerImage 图片文件
     * @param request 请求体
     * @return
     */
    @Override
    public boolean updateHeaderUrl(MultipartFile headerImage, HttpServletRequest request) throws IOException {
        String accountId = getTokenAccountId.getTokenAccountId(request);
        //获取解析token并建accountId传递给service
        String token = request.getHeader("Authorization");
        UserVo userVo = JSON.parseObject(getSetRedis.getToken(token),UserVo.class);
        System.out.println(userVo);

        String filename = headerImage.getOriginalFilename();
        FileInputStream inputStream = (FileInputStream) headerImage.getInputStream();
        //为文件重命名：uuid+filename
        filename = UUID.randomUUID()+ filename;
        String link = qiNiuYunConfig.uploadImgToQiNiu(inputStream, filename);
        if (link.equals("false")){
            throw new BlogException( Header_Url_ERROR);
        }
        String url = "http://"+path+"/"+filename;
        userVo.setHeaderUrl(url);

       User user = userMapper.selectByAccountId(accountId,"");
        getSetRedis.setToken(token,userVo);
        //七牛云删除只要图片名称，所以把域名去掉
        String deleteurl = user.getHeaderUrl().replace("http://qn.yxwhzj6.top/","");
        qiNiuYunConfig.deleteFile(deleteurl);
       int i = userMapper.updateUserImg(accountId,url);
       if (i==1){
           return true;
       }
        return false;
    }

    /**
     * 查询用户信息
     * @param accountId 用户ID
     * @return 用户Vo
     */
    @Override
    public UserVo queryUserInfo(String accountId) {
        User user = userMapper.selectByAccountId(accountId,"");
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return userVo;
    }

    @Override
    public List<UserVo> queryUserList(String condition) {
        return userMapper.queryUserlIST(condition);
    }


}
