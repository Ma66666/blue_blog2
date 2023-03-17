package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BlueBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlueBlogApplication.class,args);
        System.out.println("=======项目启动成功，欢迎使用=======");
    }

    @PostConstruct
    public void init() {
        // 解决netty启动冲突问题
        // see Netty4Utils.setAvailableProcessors()
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(BlueBlogApplication.class);
//    }
}
