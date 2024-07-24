package com.example.springbootweb.demos.threadWeb;

import com.example.springbootweb.demos.thread.constant.StrategyConstantNew;
import com.example.springbootweb.demos.thread.thread.StrategyThreadNew;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 多线程管理
 *
 * @Author Zhb
 * @create 2024/4/22 下午4:18
 */
@Slf4j
@RestController
@RequestMapping("/chargeDischargeStrategyConfiguration")
public class ThreadController {

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Resource(name = "strategyThreadPollManager")
    private ThreadPoolExecutor strategyThreadPollManager;

    /**
     * 线程池新增线程--监测线程池中线程的数量和状态
     */
    @GetMapping("/add")
    public void add(@RequestParam Integer mark) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("add");
//            }
//        });
//        thread.start();

//        String energyStorageCabinetSn = "EMS-1000";
        List<String> list = StrategyConstantNew.STRATEGY_POWER_CABINET_SN.get("EMS");
        if (mark.equals(1)) {
            list.add("EMS-100");
            list.add("EMS-101");
            list.add("EMS-102");
            list.add("EMS-103");
        } else {
            list.add("EMS-1");
            list.add("EMS-2");
            list.add("EMS-3");
            list.add("EMS-4");
        }


        StrategyConstantNew.STRATEGY_POWER_CABINET_SN.put("EMS", list);

//        StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
//        if (strategyThreadNew != null) {
//            log.error("线程存在:{}", energyStorageCabinetSn);
//        } else {
//            strategyThreadNew = new StrategyThreadNew(energyStorageCabinetSn);
////                strategyThreadNew.setNowPower(nowPower);
//            strategyThreadPollManager.submit(strategyThreadNew);
//        }

//        log.error("增加监视日志 测试成功:{}", energyStorageCabinetSn);

    }


    /**
     * 线程池关闭线程--监测线程池中线程的数量和状态
     */
    @GetMapping("/remove")
    public void stop(@RequestParam Integer mark) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("stop");
//            }
//        });
//        thread.start();

        List<String> removeList = new ArrayList<>();
        if (mark.equals(1)) {
            removeList.add("EMS-100");
            removeList.add("EMS-101");
            removeList.add("EMS-102");
            removeList.add("EMS-103");

        } else {
            removeList.add("EMS-1");
            removeList.add("EMS-2");
            removeList.add("EMS-3");
            removeList.add("EMS-4");

        }


        for (String energyStorageCabinetSn : removeList) {
            StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
            if (strategyThreadNew != null) {

                for (String testString : StrategyConstantNew.STRATEGY_POWER_CABINET_SN.get("EMS")) {
                    if (energyStorageCabinetSn.equals(testString)) {
                        log.error("变量存在:{}", energyStorageCabinetSn);
                        StrategyConstantNew.STRATEGY_POWER_CABINET_SN.get("EMS").remove(testString);
                        break;
                    }
                }
                log.error("线程存在:{}", energyStorageCabinetSn);

                strategyThreadNew.remove();

                StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.remove(energyStorageCabinetSn);
                log.error("增加监视日志 关闭成功1:{}", energyStorageCabinetSn);
            } else {
                log.error("线程不存在:{}", energyStorageCabinetSn);
            }
        }


    }

    /**
     * 线程池暂停线程--监测线程池中线程的数量和状态
     */
    @GetMapping("/pause")
    public void pause() {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("pause");
//            }
//        });
//        thread.start();


    }


}
