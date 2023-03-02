package com.blog.util.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
//在控制器Handler中 注册拦截器 -- 所有访问设置拦截
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    String[] swaggerExcludes=new String[]{"/swagger-ui.html","/swagger-resources/**","/webjars/**"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(swaggerExcludes)//不拦截swagger
                .excludePathPatterns("/api/demo/**")
                .excludePathPatterns("/api/blogShow/**")
                .excludePathPatterns("/api/Topic/**")
                .excludePathPatterns("/api/login/**")
                .excludePathPatterns("/api/Like/getUserLikeType")
        ;                    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
        super.addInterceptors(registry);
    }

    @Bean
   public AuthenticationInterceptor authenticationInterceptor() {
       return new AuthenticationInterceptor();
    }
}
