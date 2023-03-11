package com.blog.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class GetTokenAccountId {

    public static String getTokenAccountId(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        Map<String,Object> map = BlogToken.parserJavaWebToken(token);
        if (map==null){
            return "";
        }
        String accountId = (String) map.get("accountId");
        return accountId;

    }
}
