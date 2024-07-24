package com.example.springredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;

@SpringBootApplication
public class SpringBootRedisApplication {

//    @Resource
//    static Example example;

    public static void main(String[] args) throws MalformedURLException {
        SpringApplication.run(SpringBootRedisApplication.class, args);

        // 测试
//        Example example = new Example();
//        example.addLink("userid111", new URL("http://www.example.com/docs/resource1.html"));
    }

}
