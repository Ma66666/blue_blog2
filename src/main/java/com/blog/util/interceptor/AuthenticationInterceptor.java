package com.blog.util.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.util.BlogToken;
import com.blog.util.GetSetRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
@Slf4j
//拦截器的设置
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private GetSetRedis getSetRedis;
    //在controller之前拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String url = request.getRequestURI();
        log.info("---登录拦截器地址：-------"+url+"---------------------拦截器地址------");
        if (url.endsWith("api/login/login")){
            System.out.println(true);
        }
        if (url.endsWith("/api/login/login")||url.endsWith("api/login/register")
                ||url.endsWith("api/login/sendSMS")||url.endsWith("api/login/sendSMS2")||url.endsWith("api/demo/upload")){
            return true;
        } else {
            // 执行认证
            String token = request.getHeader("Authorization"); // 从 http 请求头中取出 token
            if (token ==null){
                token = "";
            }
            log.info("---登录拦截器开始：-------"+token+"---------------------拦截器------");
            log.info("---登录拦截器解码：-------"+ BlogToken.parserJavaWebToken(token)+"---------------------拦截器------");
            if (getSetRedis.hashkey(token)) {
                // 表示token合法
                // response.getWriter().write("{code:200,msg:'success'}");
                log.info("放行");
                return true;
            } else {
                // token不合法或者过期
                response.getWriter().write("{code:400,msg:'token不合法或者过期'}");
                log.info("拦截");
                return false;
            }
        }

//        return true;
    }
//在controller之后拦截
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {

    }
//在TemplateEngine之后拦截
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }

}