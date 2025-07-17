package com.example.springredis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@SpringBootTest
public class testRedis {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void abc() {

        // redis同步获得数据
        stringRedisTemplate.opsForHash().get("ZHGY_CH0_TS", "abc");


        // redis异步获得数据
        getResultTemp(null);

    }


    /**
     * redis异步获取 数据
     *
     * @param varSns
     * @return
     */
    private List<Object> getResultTemp(List<String> varSns) {

//        return redisTemplate.executePipelined((RedisCallback<List<?>>) connection -> {
//            for (String sn : varSns) {
//                if (StrUtil.isEmpty(sn)) {
//                    continue;
//                }
//                int decollatorPos = sn.lastIndexOf(varDecollator);
//                if (decollatorPos == -1) {
//                    log.info(energyStorageCabinetSn + " {}", String.format("no decollator _ of the key: " + sn));
//                    continue;
//                }
//                // 获取设备sn
//                String energyStorageCabinetSn = sn.substring(0, decollatorPos);
//                // 截取设备变量sn
//                String varName = sn.substring(decollatorPos + varDecollator.length());
//                connection.hGet(energyStorageCabinetSn.getBytes(StandardCharsets.UTF_8), varName.getBytes(StandardCharsets.UTF_8));
//            }
//            return null;
//        });
        return null;
    }
}
