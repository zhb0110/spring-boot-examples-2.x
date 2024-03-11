package com.example.springboothutool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringBootHutoolApplicationTests {

    @Test
    void contextLoads() {

        // 遥控
        remoteControlYKVar("SZWJYT_CH40_SPC__No7YK", "1");

        // 遥调，都是10倍才有用
//        remoteControlYKVar("ZHGY_CH1_PCS__ActivePowerSettingYT", "10");

    }

    /**
     * 给命令的值必须乘以10的系数，比如想下达10kw，就必须发送100的数值
     *
     * @param varSn
     * @param value
     */
    private static void remoteControlYTVar(String varSn, Double value) {

        Map<String, Object> req = new HashMap<>();
        req.put("type", "shedian");

        List<Map<String, String>> array = new ArrayList<>();
        req.put("detail", array);

        Map<String, String> map1 = new HashMap<>();
        map1.put("varsn", varSn);

        map1.put("value", String.valueOf(value * 10));
        array.add(map1);

//        HttpUtil.post("http://49.4.81.97:10081/variables/action", JSONUtil.toJsonStr(req));

        HttpRequest.post("http://49.4.81.97:10081/variables/action")
                .body(JSONUtil.toJsonStr(req))
                .timeout(20000) // 请求等待时间
                .execute().body();
    }

    /**
     * 远程遥控
     *
     * @param varSn
     * @param value
     */
    public static void remoteControlYKVar(String varSn, String value) {
        Map<String, Object> req = new HashMap<>();
        req.put("type", "yaokong-act");
        List<Map<String, String>> array = new ArrayList<>();
        req.put("detail", array);
        Map<String, String> map = new HashMap<>();
        map.put("varsn", varSn);
        map.put("value", value);
        array.add(map);
//        HttpUtil.post("http://49.4.81.97:10081/variables/action", JSONUtil.toJsonStr(req));
        HttpRequest.post("http://49.4.81.97:10081/variables/action")
                .body(JSONUtil.toJsonStr(req))
                .timeout(20000) // 请求等待时间
                .execute().body();
    }

}
