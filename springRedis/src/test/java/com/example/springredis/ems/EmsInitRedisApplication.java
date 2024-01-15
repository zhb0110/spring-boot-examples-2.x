package com.example.springredis.ems;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;

@SpringBootTest
class EmsInitRedisApplication {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 创建变压器redis
     */
    @Test
    public void create1() {
        // HASH
        HashMap<String, String> map = new HashMap<>();
        map.put("P", "20");
        stringRedisTemplate.opsForHash().putAll("ZHGY_CH0_TS", map);
    }

    /**
     * 创建动力柜redis
     */
    @Test
    public void create2() {
        // HASH
        HashMap<String, String> map = new HashMap<>();
        map.put("P", "0");
        stringRedisTemplate.opsForHash().putAll("ZHGY_CH0_EM", map);
    }


    /**
     * 创建储能柜：串口redis
     */
    @Test
    public void create3() {
        // HASH
        HashMap<String, String> map = new HashMap<>();
        map.put("No1YX", "1");
        map.put("No2YX", "1");
        map.put("No7YX", "1");
        map.put("No8YX", "1");
        stringRedisTemplate.opsForHash().putAll("ZHGY_CH8_SPC", map);
    }

    /**
     * 创建储能柜：PCS redis
     */
    @Test
    public void create4() {
        // HASH
        HashMap<String, String> map = new HashMap<>();
        map.put("SystemFullyChargedStatus", "0");
        map.put("SystemTotallyDischargedStatus", "0");
        map.put("ActivePowerSetting", "0");
        map.put("SplitPhaseControlA", "0");
        map.put("SplitPhaseControlB", "0");
        map.put("SplitPhaseControlC", "0");

        stringRedisTemplate.opsForHash().putAll("ZHGY_CH8_PCS", map);
    }

    /**
     * 创建储能柜：BCMU redis
     */
    @Test
    public void create5() {
        // HASH
        HashMap<String, String> map = new HashMap<>();
        map.put("SOC", "30");
        map.put("CumulativeDischarge", "100");
        map.put("MinU", "3.270");
        map.put("CumulativeCharge", "11");
        map.put("MaxU", "2.7");

//        for (int i = 0; i < 144; i++) {
//            map.put("T" + (i + 1), "1");
//        }
        stringRedisTemplate.opsForHash().putAll("ZHGY_CH3_BCMU", map);
    }

}