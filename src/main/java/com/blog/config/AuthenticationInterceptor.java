package com.blog.config;//package com.blog.config;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.blog.result.blog_token;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
////拦截器的设置
//public class AuthenticationInterceptor implements HandlerInterceptor {
//    //在controller之前拦截
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        String url = request.getRequestURI();
//        log.info("---登录拦截器地址：-------"+url+"---------------------拦截器地址------");
//        // 如果不是登录操作 判断 session
//        if (!url.endsWith("api/demo/login")) {
//            // 执行认证
//            String token = request.getHeader("token"); // 从 http 请求头中取出 token
//            log.info("---登录拦截器开始：-------"+token+"---------------------拦截器------");
//            log.info("---登录拦截器解码：-------"+blog_token.parserJavaWebToken(token)+"---------------------拦截器------");
//            if (blog_token.parserJavaWebToken(token) != null) {
//                // 表示token合法
//                // response.getWriter().write("{code:200,msg:'success'}");
//                return true;
//            } else {
//                // token不合法或者过期
//                // response.getWriter().write("{code:400,msg:'token不合法或者过期'}");
//                return false;
//            }
//        }
//        return true;
//    }
////在controller之后拦截
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
//                           ModelAndView modelAndView) throws Exception {
//
//    }
////在TemplateEngine之后拦截
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
//                                Object o, Exception e) throws Exception {
//
//    }
//
//}