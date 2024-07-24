package com.example.springredis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;

@SpringBootTest
class RedisdemoApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {
        // Set类型
//        stringRedisTemplate.opsForSet().add("XXX", "C");

        // HASH
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < 144; i++) {
            map.put("T" + (i + 1), "1");
        }
        stringRedisTemplate.opsForHash().putAll("ZHGY_CH3_BCMU", map);


//        List<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        HashMap<String,String> map = new HashMap<>();
//
//
//        stringRedisTemplate.opsForValue().set("abc", "测试"); // String类型 单个设置，只有值
//        stringRedisTemplate.opsForList().leftPushAll("qq", list); // List类型 顺序 Index Element
//        stringRedisTemplate.opsForList().range("qwe", 0, -1).forEach(value -> {
//            System.out.println(value);
//        });
    }

}