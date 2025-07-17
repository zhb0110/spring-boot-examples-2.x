package com.example.javatest;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootJavaTestApplicationTests {

    @Test
    void contextLoads() {

// 数值比较
        Double a = 100d;
        Integer b = 100;
        if (a.compareTo(b.doubleValue()) == 0) {
            System.out.println("相等");
        } else {
            System.out.println("不相等");
        }
        if (a >= b) {
            System.out.println("相等1");
        } else {
            System.out.println("不相等1");
        }
    }

    @Test
    public void test() {
        String message = "{\"func\":\"expire_ack\",\"index\":5,\"pieces\":6,\"random\":\"112\",\"result\":[{\"dt\":\"1725291309789\",\"value_0\":\"\"},\n" +
                "{\"dt\":\"1725291371701\",\"value_0\":\"\"},{\"dt\":\"1725291433604\",\"value_0\":\"\"},{\"dt\":\"1725291495597\",\"value_0\":\"\"},\n" +
                "{\"dt\":\"1725291557519\",\"value_0\":\"\"},{\"dt\":\"1725291609433\",\"value_0\":\"\"},{\"dt\":\"1725291671385\",\"value_0\":\"\"},\n" +
                "{\"dt\":\"1725291733374\",\"value_0\":\"\"},{\"dt\":\"1725291795345\",\"value_0\":\"\"},{\"dt\":\"1725291857293\",\"value_0\":\"\"},\n" +
                "{\"dt\":\"1725291919274\",\"value_0\":\"\"},{\"dt\":\"1725291981226\",\"value_0\":\"\"},{\"dt\":\"1725292033113\",\"value_0\":\"\"},\n" +
                "{\"dt\":\"1725292095061\",\"value_0\":\"\"},{\"dt\":\"1725292157033\",\"value_0\":\"\"},{\"dt\":\"1725292219002\",\"value_0\":\"\"},\n" +
                "{\"dt\":\"1725292280964\",\"value_0\":\"\"},{\"dt\":\"1725292342911\",\"value_0\":\"\"},{\"dt\":\"1725292404843\",\"value_0\":\"\"},\n" +
                "{\"dt\":\"1725292456821\",\"value_0\":\"\"},{\"dt\":\"1725292518756\",\"value_0\":\"\"},{\"dt\":\"1725292580697\",\"value_0\":\"\"},\n" +
                "{\"dt\":\"1725292642659\",\"value_0\":\"\"},{\"dt\":\"1725292704585\",\"value_0\":\"\"}],\"return\":\"Success\"}";
        JSONObject messageJSON = new JSONObject(message);
        JSONArray result = messageJSON.getJSONArray("result");
        for (Object dataObject : result) {
            JSONObject data = (JSONObject) dataObject;
            String dt = data.getStr("dt");
//    String valueString = data.keys().
            System.out.println(dt);
        }

    }

}
