package com.example.springredis.ems;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;

/**
 * 模拟一个储能柜
 */
@SpringBootTest
public class EmsRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 放电前（初始化）
    private String[] data_1 = new String[]{
            "0", "0", "1",
            "1", "1", "1", "1", // 串口
            "0", "0", "-35", "-11", "-11", "-11", "0", "0", "2", "1", // PCS
            "30", "0", "2.7", "0", "3.270" // BCMU
    };
    // 放空（静置，充电前）
    private String[] data_2 = new String[]{
            "0", "0", "1",
            "1", "1", "1", "1", // 串口
            "0", "1", "-35", "-11", "-11", "-11", "0", "0", "2", "1", // PCS
            "0", "69", "2.6", "0", "3.10" // BCMU
    };
    // 充满
    private String[] data_3 = new String[]{
            "0", "0", "1",
            "1", "1", "1", "1", // 串口
            "1", "0", "100", "33", "33", "33", "0", "0", "2", "1", // PCS
            "100", "69", "2.8", "230", "3.30" // BCMU
    };
    // 第二次放空
    private String[] data_4 = new String[]{
            "0", "0", "1",
            "1", "1", "1", "1", // 串口
            "0", "1", "-35", "-35", "-35", "-35", "0", "0", "2", "1", // PCS
            "0", "299", "2.6", "230", "3.10" // BCMU
    };

    HashMap<String, String> dataDemo = new HashMap<>();

    /**
     * 创建一个变压器，动力柜，储能柜
     */
    @Test
    public void initEmsStation() {
        // 变压器sn
        String transformerSn = "ZHGY_CH0_TS";
        // 动力柜sn
        String powerCabinetSn = "ZHGY_CH0_EM";
        // 储能柜通道sn，根据现场的情况更改
        String energyStorageCabinetSn = "ZHGY_CH4";

        // TODO:只需要更改此处即可测试
        String[] data = data_4;

        // 初始化值
        dataDemo.put("transformerSn__P", data[0]); // 变压器功率
        dataDemo.put("powerCabinetSn__P", data[1]); // 动力柜功率
        dataDemo.put("CD__energyStorageCabinetSn__COMMUNICATION_STATUS", data[2]); // 储能柜在线

        dataDemo.put("energyStorageCabinetSn_SPC__No1YX", data[3]); // 串口
        dataDemo.put("energyStorageCabinetSn_SPC__No2YX", data[4]); // 串口
        dataDemo.put("energyStorageCabinetSn_SPC__No3YX", data[5]); // 串口
        dataDemo.put("energyStorageCabinetSn_SPC__No4YX", data[6]); // 串口

        dataDemo.put("energyStorageCabinetSn_PCS__SystemFullyChargedStatus", data[7]); // PCS 满充
        dataDemo.put("energyStorageCabinetSn_PCS__systemTotallyDischargedStatus", data[8]); // PCS 满放
        dataDemo.put("energyStorageCabinetSn_PCS__ActivePowerSetting", data[9]); // PCS
        dataDemo.put("energyStorageCabinetSn_PCS__SplitPhaseControlA", data[10]); // PCS
        dataDemo.put("energyStorageCabinetSn_PCS__SplitPhaseControlB", data[11]); // PCS
        dataDemo.put("energyStorageCabinetSn_PCS__SplitPhaseControlC", data[12]); // PCS
        dataDemo.put("energyStorageCabinetSn_PCS__SystemFaultStatus", data[13]); // PCS
        dataDemo.put("energyStorageCabinetSn_PCS__SystemAlarmStatus", data[14]); // PCS
        dataDemo.put("energyStorageCabinetSn_PCS__ControlMode", data[15]); // PCS
        dataDemo.put("energyStorageCabinetSn_PCS__SystemOnOffStatus", data[16]); // PCS

        dataDemo.put("energyStorageCabinetSn_BCMU__SOC", data[17]); // BCMU
        dataDemo.put("energyStorageCabinetSn_BCMU__CumulativeDischarge", data[18]); // BCMU
        dataDemo.put("energyStorageCabinetSn_BCMU__MinU", data[19]); // BCMU
        dataDemo.put("energyStorageCabinetSn_BCMU__CumulativeCharge", data[20]); // BCMU
        dataDemo.put("energyStorageCabinetSn_BCMU__MaxU", data[21]); // BCMU

        // 变压器
        initTransformer(transformerSn);

        // 动力柜
        initPowerCabinet(powerCabinetSn);

        // 储能柜
        initEnergyStorageCabinet(energyStorageCabinetSn);
    }

    /**
     * 初始化变压器
     */
    private void initTransformer(String transformerSn) {
        HashMap<String, String> map = new HashMap<>();
        map.put("P", dataDemo.get("transformerSn__P"));
        stringRedisTemplate.opsForHash().putAll(transformerSn, map);
    }

    /**
     * 初始化动力柜
     */
    private void initPowerCabinet(String powerCabinetSn) {
        HashMap<String, String> map = new HashMap<>();
        map.put("P", dataDemo.get("powerCabinetSn__P"));
        stringRedisTemplate.opsForHash().putAll(powerCabinetSn, map);
    }

    /**
     * 初始化储能柜
     */
    private void initEnergyStorageCabinet(String energyStorageCabinetSn) {
        // 储能柜在线
        HashMap<String, String> map = new HashMap<>();
        map.put("COMMUNICATION_STATUS", dataDemo.get("CD__energyStorageCabinetSn__COMMUNICATION_STATUS"));
        stringRedisTemplate.opsForHash().putAll("CD__" + energyStorageCabinetSn, map);

        // 储能柜串口
        initEnergyStorageCabinet_gorgeLine(energyStorageCabinetSn);

        // 储能柜PCS
        initEnergyStorageCabinet_PCS(energyStorageCabinetSn);

        // 储能柜BCMU
        initEnergyStorageCabinet_BCMU(energyStorageCabinetSn);

    }

    /**
     * 初始化储能柜串口
     */
    private void initEnergyStorageCabinet_gorgeLine(String energyStorageCabinetSn) {
        HashMap<String, String> map = new HashMap<>();
        map.put("No1YX", dataDemo.get("energyStorageCabinetSn_SPC__No1YX"));
        map.put("No2YX", dataDemo.get("energyStorageCabinetSn_SPC__No2YX"));
        map.put("No7YX", dataDemo.get("energyStorageCabinetSn_SPC__No3YX"));
        map.put("No8YX", dataDemo.get("energyStorageCabinetSn_SPC__No4YX"));
        stringRedisTemplate.opsForHash().putAll(energyStorageCabinetSn + "_SPC", map);
    }

    /**
     * 初始化储能柜PCS
     */
    private void initEnergyStorageCabinet_PCS(String energyStorageCabinetSn) {
        HashMap<String, String> map = new HashMap<>();
        map.put("SystemFullyChargedStatus", dataDemo.get("energyStorageCabinetSn_PCS__SystemFullyChargedStatus")); // 满充
        map.put("SystemTotallyDischargedStatus", dataDemo.get("energyStorageCabinetSn_PCS__systemTotallyDischargedStatus")); // 满放
        map.put("ActivePowerSetting", dataDemo.get("energyStorageCabinetSn_PCS__ActivePowerSetting"));
        map.put("SplitPhaseControlA", dataDemo.get("energyStorageCabinetSn_PCS__SplitPhaseControlA"));
        map.put("SplitPhaseControlB", dataDemo.get("energyStorageCabinetSn_PCS__SplitPhaseControlB"));
        map.put("SplitPhaseControlC", dataDemo.get("energyStorageCabinetSn_PCS__SplitPhaseControlC"));

        // 是否故障：0不故障，1故障
        map.put("SystemFaultStatus", dataDemo.get("energyStorageCabinetSn_PCS__SystemFaultStatus"));
        // 是否告警：0不告警，1告警
        map.put("SystemAlarmStatus", dataDemo.get("energyStorageCabinetSn_PCS__SystemAlarmStatus"));
        // 230A:控制模式：本地0，远程2
        map.put("ControlMode", dataDemo.get("energyStorageCabinetSn_PCS__ControlMode"));
//        map.put("ControlModeYC", "0");
//        // 230A+:控制模式：本地0，远程1
//        map.put("ControlMode", "0");

        // 启动状态
        map.put("SystemOnOffStatus", dataDemo.get("energyStorageCabinetSn_PCS__SystemOnOffStatus"));

        stringRedisTemplate.opsForHash().putAll(energyStorageCabinetSn + "_PCS", map);
    }

    /**
     * 初始化储能柜BCMU
     */
    private void initEnergyStorageCabinet_BCMU(String energyStorageCabinetSn) {
        HashMap<String, String> map = new HashMap<>();
        map.put("SOC", dataDemo.get("energyStorageCabinetSn_BCMU__SOC"));
        map.put("CumulativeDischarge", dataDemo.get("energyStorageCabinetSn_BCMU__CumulativeDischarge"));
        map.put("MinU", dataDemo.get("energyStorageCabinetSn_BCMU__MinU"));
        map.put("CumulativeCharge", dataDemo.get("energyStorageCabinetSn_BCMU__CumulativeCharge"));
        map.put("MaxU", dataDemo.get("energyStorageCabinetSn_BCMU__MaxU"));

//        for (int i = 0; i < 144; i++) {
//            map.put("T" + (i + 1), "1");
//        }
        stringRedisTemplate.opsForHash().putAll(energyStorageCabinetSn + "_BCMU", map);
    }


}
