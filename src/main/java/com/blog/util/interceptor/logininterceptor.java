package com.blog.util.interceptor;

import com.blog.util.GetSetRedis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class logininterceptor implements HandlerInterceptor {
@Autowired
private GetSetRedis getSetRedis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           String token = request.getHeader("token");
           if (token.equals(getSetRedis.getToken(token))){
               return true;
           }
        return false;
    }
}
