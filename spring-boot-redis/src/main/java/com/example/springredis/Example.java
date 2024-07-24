package com.example.springredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Example {

    // inject the actual template 注入实际模板
    @Autowired
    private RedisTemplate template;

    // inject the template as ListOperations 将模板注入为ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations 也可以注入为值，设置，ZSet，和HashOperations
    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    public void addLink(String userId, URL url) {
//        listOps.leftPush(userId, url.toExternalForm());
        // or use template directly 或直接使用模板
        System.out.println("userId = " + userId + ", url = " + url);
        template.boundListOps(userId).leftPush(url.toExternalForm());
    }
}
