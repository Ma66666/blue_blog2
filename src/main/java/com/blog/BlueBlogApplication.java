package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BlueBlogApplication {
    public static void main(String[] args) {

        SpringApplication.run(BlueBlogApplication.class,args);
        System.out.println("=======项目启动成功，欢迎使用=======");
    }
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(BlueBlogApplication.class);
//    }
}
