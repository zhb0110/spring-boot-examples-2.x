package com.example.springredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class SpringRedisApplication {

//    @Resource
//    static Example example;

    public static void main(String[] args) throws MalformedURLException {
        SpringApplication.run(SpringRedisApplication.class, args);

        // 测试
//        Example example = new Example();
//        example.addLink("userid111", new URL("http://www.example.com/docs/resource1.html"));
    }

}
