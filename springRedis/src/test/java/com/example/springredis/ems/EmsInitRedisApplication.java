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
     * 创建储能柜：在线
     */
    @Test
    public void create3_1() {
        // HASH
        HashMap<String, String> map = new HashMap<>();
        map.put("COMMUNICATION_STATUS", "1");
        stringRedisTemplate.opsForHash().putAll("CD__ZHGY_CH8", map);
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
        map.put("SystemFullyChargedStatus", "0"); // 满充
        map.put("SystemTotallyDischargedStatus", "0"); // 满放
        map.put("ActivePowerSetting", "0");
        map.put("SplitPhaseControlA", "0");
        map.put("SplitPhaseControlB", "0");
        map.put("SplitPhaseControlC", "0");

        // 是否故障：0不故障，1故障
        map.put("SystemFaultStatus", "0");
        // 是否告警：0不告警，1告警
        map.put("SystemAlarmStatus", "0");
        // 230A:控制模式：本地0，远程2
        map.put("ControlMode", "0");
//        map.put("ControlModeYC", "0");
//        // 230A+:控制模式：本地0，远程1
//        map.put("ControlMode", "0");

        // 启动状态
        map.put("SystemOnOffStatus", "1");

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
        stringRedisTemplate.opsForHash().putAll("ZHGY_CH8_BCMU", map);
    }

}