package com.example.springbootweb.demos.thread.thread;

//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.http.HttpRequest;
//import cn.hutool.json.JSONUtil;
//import com.eraworks.device.api.entity.ChargeDischargeStrategyTemplateDetail;
//import com.eraworks.device.api.entity.EmsDeviceModel;
//import com.eraworks.device.enums.LogType;
//import com.eraworks.device.strategy2.constant.StrategyConstantNew;
//import com.eraworks.device.util.LogTools;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author Zhb
 * @create 2024/01/08
 * 充放电策略线程
 */
@Slf4j
public class StrategyThreadNew extends Thread {

    private static Logger logger = LoggerFactory.getLogger(StrategyThreadNew.class);

    // 执行服务，用于定时任务
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    // 执行服务，用于关闭spc，必要
    ScheduledExecutorService closeSPCExecutor = Executors.newSingleThreadScheduledExecutor();

    // 线程常量：
//    private RedisTemplate redisTemplate;
    private final static String varDecollator = "__";

    // 变压器sn
    private String transformerSn;
    // 充放电策略模板id
    private Integer chargeDischargeStrategyTemplateId;
    // 储能柜真实sn
    private String energyStorageCabinetSn;
    // 通道信息
    private String monitorChannelSn;
    // 储能柜型号：额定功率，总容量
//    private EmsDeviceModel emsDeviceModel;
//    // 充放电策略模板明细
//    private List<ChargeDischargeStrategyTemplateDetail> chargeDischargeStrategyTemplateDetailList;


    // 线程本身：存放一些状态量
    // 状态：是否停止，通过这种方法来控制线程是否停止，默认是开启的，否则会直接走完线程，除非是停止或完成，否则永远是false
    private volatile boolean isStop = false;
    // 是否充满
    private volatile boolean fullCharge = false;
    // 是否放空
    private volatile boolean fullDischarge = false;
    // 是否在执行中
    private volatile boolean isRunning = false;

    // bcmu的SOC
    private volatile Double bcmuSoc = 0d;

    // 单次充电如果电压高于限值，则不可再恢复满功率
    public volatile AtomicBoolean isAboveTheLimit = new AtomicBoolean(false);

    // 单次放电如果电压低于限值，则不可再恢复满功率
    public volatile AtomicBoolean isBelowTheLimit = new AtomicBoolean(false);

    // 储能柜：本身的数值
    // 实际下发功率
    private volatile Double nowPower = 0d;


    public StrategyThreadNew(String energyStorageCabinetSn) {
//        this.redisTemplate = redisTemplate;
        this.transformerSn = transformerSn;
        this.chargeDischargeStrategyTemplateId = chargeDischargeStrategyTemplateId;
        this.energyStorageCabinetSn = energyStorageCabinetSn;

        // 初始化
        this.initParam();
    }

//    public StrategyThreadNew(RedisTemplate redisTemplate, String energyStorageCabinetSn) {
//        this.redisTemplate = redisTemplate;
//        this.transformerSn = transformerSn;
//        this.chargeDischargeStrategyTemplateId = chargeDischargeStrategyTemplateId;
//        this.energyStorageCabinetSn = energyStorageCabinetSn;
//
//        // 初始化
//        this.initParam();
//    }

    /**
     * @param redisTemplate
     * @param transformerSn                     变压器sn
     * @param chargeDischargeStrategyTemplateId 充放电策略模板id
     * @param energyStorageCabinetSn            储能柜真实sn
     */
//    public StrategyThreadNew(RedisTemplate redisTemplate, String transformerSn, Integer chargeDischargeStrategyTemplateId, String energyStorageCabinetSn) {
//        this.redisTemplate = redisTemplate;
//        this.transformerSn = transformerSn;
//        this.chargeDischargeStrategyTemplateId = chargeDischargeStrategyTemplateId;
//        this.energyStorageCabinetSn = energyStorageCabinetSn;
//
//        // 初始化
//        this.initParam();
//    }

    private void initParam() {
        // 得到通道信息-通道信息可能会变，储能柜的通道，而不是变压器的通道，内部存储的就是储能柜通道号
//        this.monitorChannelSn = StrategyConstantNew.STRATEGY_CABINET_SN_PRODUCT_DEVICE_SN.get(energyStorageCabinetSn);
//        // 策略模板明细
//        this.chargeDischargeStrategyTemplateDetailList = StrategyConstantNew.STRATEGY_TEMPLATE_DETAIL.get(chargeDischargeStrategyTemplateId);
//        // 储能柜型号信息
//        this.emsDeviceModel = StrategyConstantNew.STRATEGY_CABINET_SN_EMSDEVICEMODEL.get(energyStorageCabinetSn);
    }

    @Override
    public void run() {
//        refreshSOC();
        while (!isStop) {
            try {
                //死循环内适当休眠避免占用过高CPU影响服务器性能
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            log.error("子线程打开：{}",energyStorageCabinetSn);

            // 再次获得值
//            this.initParam();
//            LocalDateTime now = LocalDateTime.now();
//            for (ChargeDischargeStrategyTemplateDetail detail : this.chargeDischargeStrategyTemplateDetailList) {
//                LocalDateTime startTime = detail.getStartTime();
//                LocalDateTime endTime = detail.getEndTime();
//                LocalDateTime strategyStart = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), startTime.getHour(), startTime.getMinute());
//                LocalDateTime strategyEnd = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), endTime.getHour(), endTime.getMinute());
//                if (now.isAfter(strategyStart) && now.isBefore(strategyEnd) && !isRunning) {
//                    switch (detail.getStrategyType()) {
//                        case 0:
//                            //充电
//                            if (fullCharge || this.bcmuSoc.compareTo(detail.getLimitSoc().doubleValue()) >= 0) {
////                            LogTools.log(logger,monitorChannelSn, LogType.INFO,String.format("%s已经充满",monitorChannelSn);
//                            } else {
//                                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-充电策略开始执行:%s", DateUtil.now(), energyStorageCabinetSn));
//                                startCharge(detail);
//                            }
//                            break;
//                        case 1:
//                            //放电
//                            if (fullDischarge || this.bcmuSoc.compareTo(detail.getLimitSoc().doubleValue()) <= 0) {
////                            LogTools.log(logger,monitorChannelSn, LogType.INFO,String.format("%s已经放空",monitorChannelSn);
//                            } else {
//                                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-放电策略开始执行:%s", DateUtil.now(), energyStorageCabinetSn));
//                                startDisCharge(detail);
//                            }
//                            break;
//                        default:
//                            break;
//                    }
////                    refreshAvgSOC();
//                }
//            }
        }
//        LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s对应储能柜的策略线程已经手动终止结束:%s", energyStorageCabinetSn, getName(), energyStorageCabinetSn));
        executorService.shutdown();
    }

    /**
     * 充电
     *
     * @param strategyDetail
     */
//    public void startCharge(ChargeDischargeStrategyTemplateDetail strategyDetail) {
//        LocalDateTime nowTime = LocalDateTime.now();
//        LocalDateTime strategyEnd = LocalDateTime.of(nowTime.getYear(), nowTime.getMonth(), nowTime.getDayOfMonth(),
//                strategyDetail.getEndTime().getHour(), strategyDetail.getEndTime().getMinute());
//        // 防止设备异常一直在下发控制令
//        while ((!getIsOnline() || !checkSPCStatus()) && !LocalDateTime.now().isAfter(strategyEnd) && !this.isStop) {
//            // 条件不符合，则循环
//            // 在时间范围内true，在时间范围外false
//            // 如果线程在运行中则true，关闭则false
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s储能柜不在线，或通道下的串口控制器尚未全部打开，或PCS报警/故障、不在远程模式、未启动，且未超时，重新发送遥控命令打开串口", monitorChannelSn));
//        }
//
//        // 验证是否超时，超时直接关闭
//        if (LocalDateTime.now().isAfter(strategyEnd)) {
//            // 直接关闭
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-超时：充电时间截止,已停止充电%s", DateUtil.now(), energyStorageCabinetSn));
//
//            // 保险：检查是否停止充电，并下令停止充电
////                closeSPCExecutor.schedule(pcsYT, 1, TimeUnit.MINUTES);
//
//            //2023-07-20新增逻辑，充放电结束后立刻关闭PCS的风扇，30分钟后关闭pack风扇
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s，超时：立刻关闭7串口，30分钟后关闭8串口", monitorChannelSn));
//            remoteControlYKVar(monitorChannelSn + "_SPC__No7YK", "0", false);
//            closeSPCExecutor.schedule(closeSPC, 30, TimeUnit.MINUTES);
//            return;
//        }
//
//        //在策略时间内，并且没有在执行策略，开始执行策略
//        isRunning = true;
//        // 初始化
//        isAboveTheLimit.set(false);
//
//        Runnable task = () -> {
//            refreshSOC();
//            System.err.println("WO JIN LAI LE");
//
//            // 超时不再执行
//            LocalDateTime now = LocalDateTime.now();
//            if (now.isAfter(strategyEnd)) {
//                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-充电时间截止,已停止充电%s", DateUtil.now(), energyStorageCabinetSn));
//                if ("EWES-230A+".equals(this.emsDeviceModel.getModel())) {
//                    remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlAYT", 0.0, false);
//                    try {
//                        // 防止丢令，休眠1秒
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlBYT", 0.0, false);
//                    try {
//                        // 防止丢令，休眠1秒
//                        Thread.sleep(1500);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlCYT", 0.0, false);
//                } else {
//                    remoteControlYTVar(monitorChannelSn + "_PCS__ActivePowerSettingYT", 0.0, false);
//                }
//                // 保险：检查是否停止充电，并下令停止充电
////                closeSPCExecutor.schedule(pcsYT, 1, TimeUnit.MINUTES);
//
//                //2023-07-20新增逻辑，充放电结束后立刻关闭PCS的风扇，30分钟后关闭pack风扇
//                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s，立刻关闭7串口，30分钟后关闭8串口", monitorChannelSn));
//                remoteControlYKVar(monitorChannelSn + "_SPC__No7YK", "0", false);
//                closeSPCExecutor.schedule(closeSPC, 30, TimeUnit.MINUTES);
//                executorService.shutdown();
//                isRunning = false;
//                executorService = Executors.newSingleThreadScheduledExecutor();
//                return;
//            }
//            // 获得实时数据
//            List<String> list = Arrays.asList(
//                    monitorChannelSn + "_BCMU__MaxU",
//                    monitorChannelSn + "_PCS__ActivePowerSetting",
//                    monitorChannelSn + "_BCMU__SOC",
//                    monitorChannelSn + "_PCS__SplitPhaseControlA",
//                    monitorChannelSn + "_PCS__SplitPhaseControlB",
//                    monitorChannelSn + "_PCS__SplitPhaseControlC"
//            );
//            Map<String, Double> resultTempMap = getResultTempMap(list);
//            if (resultTempMap == null) {
//                resultTempMap = new HashMap<>();
//            }
//            Double maxU = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_BCMU__MaxU", 0.0D);
//            Double avgSOC = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_BCMU__SOC", 0.0D);
//            //首先判断有没有充满
//            if (fullCharge || avgSOC.compareTo(strategyDetail.getLimitSoc().doubleValue()) >= 0) {
//                // 充满则不再执行
//                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("发送停止充电SD命令，电池已充满或已经到限制值，当前时间：%s,柜子信息：%s,SOC为：%s", DateUtil.now(), energyStorageCabinetSn, avgSOC));
//                if ("EWES-230A+".equals(this.emsDeviceModel.getModel())) {
//                    remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlAYT", 0.0, false);
//                    try {
//                        // 防止丢令，休眠1秒
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlBYT", 0.0, false);
//                    try {
//                        // 防止丢令，休眠1秒
//                        Thread.sleep(1500);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlCYT", 0.0, false);
//                } else {
//                    remoteControlYTVar(monitorChannelSn + "_PCS__ActivePowerSettingYT", 0.0, false);
//                }
//                //2023-07-20新增逻辑，充放电结束后立刻关闭PCS的风扇，30分钟后关闭pack风扇
//                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s，立刻关闭7串口，30分钟后关闭8串口", monitorChannelSn));
//                remoteControlYKVar(monitorChannelSn + "_SPC__No7YK", "0", false);
//                closeSPCExecutor.schedule(closeSPC, 30, TimeUnit.MINUTES);
////                powerStatus = 0;
//                executorService.shutdown();
//                isRunning = false;
//                executorService = Executors.newSingleThreadScheduledExecutor();
//
//            } else {
//                double activePowerSettingYT = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__ActivePowerSetting", 0.0D);
//                if ("EWES-230A+".equals(this.emsDeviceModel.getModel())) {
//                    activePowerSettingYT = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__SplitPhaseControlA", 0.0D) + MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__SplitPhaseControlB", 0.0D) + MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__SplitPhaseControlC", 0.0D);
//                }
//                // 未充满执行
//                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("getChargeMinActivePower-%s,实际下发值：%s", monitorChannelSn, nowPower));
//                if (Math.abs(nowPower - activePowerSettingYT) > 5) {
//                    LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("发送充电功率调整SD命令，调整前充电功率为：%s，调整后充电功率为：%s,柜子信息为:%s,当前时间为:%s,SOC为:%s,当前最大电压为：%s"
//                            , activePowerSettingYT, nowPower, energyStorageCabinetSn, DateUtil.now(), avgSOC, maxU));
//
//                    if ("EWES-230A+".equals(this.emsDeviceModel.getModel())) {
//                        remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlAYT", nowPower / 3, false);
//                        try {
//                            // 防止丢令，休眠1秒
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                        }
//                        remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlBYT", nowPower / 3, false);
//                        try {
//                            // 防止丢令，休眠1秒
//                            Thread.sleep(1500);
//                        } catch (InterruptedException e) {
//                        }
//                        remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlCYT", nowPower / 3, false);
//                    } else {
//                        remoteControlYTVar(monitorChannelSn + "_PCS__ActivePowerSettingYT", nowPower, false);
//                    }
//                }
//            }
//        };
//        executorService.scheduleWithFixedDelay(task, 0L, 30L, TimeUnit.SECONDS);
//    }

    /**
     * 放电
     *
     * @param strategyDetail
     */
//    public void startDisCharge(ChargeDischargeStrategyTemplateDetail strategyDetail) {
//        LocalDateTime nowTime = LocalDateTime.now();
//        LocalDateTime strategyEnd = LocalDateTime.of(nowTime.getYear(), nowTime.getMonth(), nowTime.getDayOfMonth(),
//                strategyDetail.getEndTime().getHour(), strategyDetail.getEndTime().getMinute());
//        // 防止设备异常一直在下发控制令
//        while ((!getIsOnline() || !checkSPCStatus()) && !LocalDateTime.now().isAfter(strategyEnd) && !this.isStop) {
//            // 条件不符合，则循环
//            // 在时间范围内true，在时间范围外false
//            // 如果线程在运行中则true，关闭则false
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s储能柜不在线，或通道下的串口控制器尚未全部打开，或PCS报警/故障、不在远程模式、未启动，且未超时，重新发送遥控命令打开串口", monitorChannelSn));
//        }
//
////        // 验证是否超时，超时直接关闭
//        if (LocalDateTime.now().isAfter(strategyEnd)) {
//            // 直接关闭
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-超时：放电时间截止,已停止充电%s", DateUtil.now(), energyStorageCabinetSn));
//
//            // 保险：检查是否停止充电，并下令停止充电
////                closeSPCExecutor.schedule(pcsYT, 1, TimeUnit.MINUTES);
//
//            //2023-07-20新增逻辑，充放电结束后立刻关闭PCS的风扇，30分钟后关闭pack风扇
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s，超时：立刻关闭7串口，30分钟后关闭8串口", monitorChannelSn));
//            remoteControlYKVar(monitorChannelSn + "_SPC__No7YK", "0", false);
//            closeSPCExecutor.schedule(closeSPC, 30, TimeUnit.MINUTES);
//            return;
//        }
//
//        //在策略时间内，并且没有在执行策略，开始执行策略
//        isRunning = true;
//        // 初始化
//        isBelowTheLimit.set(false);
//        Runnable task = () -> {
//            refreshSOC();
//            LocalDateTime now = LocalDateTime.now();
//            // 超时关闭
//            if (now.isAfter(strategyEnd)) {
//                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-放电时间截止,已停止放电%s", DateUtil.now(), energyStorageCabinetSn));
//                if ("EWES-230A+".equals(this.emsDeviceModel.getModel())) {
//                    remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlAYT", 0.0, false);
//                    try {
//                        // 防止丢令，休眠1秒
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                    }
//                    remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlBYT", 0.0, false);
//                    try {
//                        // 防止丢令，休眠1秒
//                        Thread.sleep(1500);
//                    } catch (InterruptedException e) {
//                    }
//                    remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlCYT", 0.0, false);
//                } else {
//                    remoteControlYTVar(monitorChannelSn + "_PCS__ActivePowerSettingYT", 0.0, false);
//                }
//                // 保险：检查是否停止充电，并下令停止充电
////                closeSPCExecutor.schedule(pcsYT, 1, TimeUnit.MINUTES);
//                //2023-07-20新增逻辑，充放电结束后立刻关闭PCS的风扇，30分钟后关闭pack风扇
//                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s，立刻关闭7串口，30分钟后关闭8串口", monitorChannelSn));
//                remoteControlYKVar(monitorChannelSn + "_SPC__No7YK", "0", false);
//                closeSPCExecutor.schedule(closeSPC, 30, TimeUnit.MINUTES);
//
//                executorService.shutdown();
//                isRunning = false;
//                executorService = Executors.newSingleThreadScheduledExecutor();
//            } else {
//                // 获得实时数据
//                List<String> list = Arrays.asList(
//                        monitorChannelSn + "_BCMU__MinU",
//                        monitorChannelSn + "_PCS__ActivePowerSetting",
//                        monitorChannelSn + "_BCMU__SOC",
//                        monitorChannelSn + "_PCS__SplitPhaseControlA",
//                        monitorChannelSn + "_PCS__SplitPhaseControlB",
//                        monitorChannelSn + "_PCS__SplitPhaseControlC"
//                );
//                Map<String, Double> resultTempMap = getResultTempMap(list);
//                if (resultTempMap == null) {
//                    resultTempMap = new HashMap<>();
//                }
//
//                Double avgSOC = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_BCMU__SOC", 0.0D);
//                //首先判断有没有放空
//                if (fullDischarge || avgSOC.compareTo(strategyDetail.getLimitSoc().doubleValue()) <= 0) {
//                    // 放空关闭
//                    LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("发送停止放电SD命令，电池已放空，当前时间：%s,柜子信息：%s,SOC为：%s", DateUtil.now(), monitorChannelSn, avgSOC));
//                    if ("EWES-230A+".equals(this.emsDeviceModel.getModel())) {
//                        remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlAYT", 0.0, false);
//                        try {
//                            // 防止丢令，休眠1秒
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                        }
//                        remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlBYT", 0.0, false);
//                        try {
//                            // 防止丢令，休眠1秒
//                            Thread.sleep(1500);
//                        } catch (InterruptedException e) {
//                        }
//                        remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlCYT", 0.0, false);
//                    } else {
//                        remoteControlYTVar(monitorChannelSn + "_PCS__ActivePowerSettingYT", 0.0, false);
//                    }
//
//                    // 保险：检查是否停止充电，并下令停止充电
////                closeSPCExecutor.schedule(pcsYT, 1, TimeUnit.MINUTES);
//
//                    //2023-07-20新增逻辑，充放电结束后立刻关闭PCS的风扇，30分钟后关闭pack风扇
//                    LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s，立刻关闭7串口，30分钟后关闭8串口", monitorChannelSn));
//                    remoteControlYKVar(monitorChannelSn + "_SPC__No7YK", "0", false);
//                    closeSPCExecutor.schedule(closeSPC, 30, TimeUnit.MINUTES);
//                    executorService.shutdown();
//                    isRunning = false;
//                    executorService = Executors.newSingleThreadScheduledExecutor();
////                powerStatus = 0;
//                    return;
//                }
//                // 未放空继续放
//                double activePowerSetting = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__ActivePowerSetting", 0.0D);
//                if ("EWES-230A+".equals(this.emsDeviceModel.getModel())) {
//                    activePowerSetting = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__SplitPhaseControlA", 0.0D) + MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__SplitPhaseControlB", 0.0D) + MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__SplitPhaseControlC", 0.0D);
//                }
//                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("getChargeMinActivePower-%s,实际下发值：%s", monitorChannelSn, nowPower));
//                if (Math.abs(nowPower - activePowerSetting) > 5) {
//                    Double minU = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_BCMU__MinU", 0.0D);
//                    LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("发送放电功率调整SD命令，调整前放电功率为：%s，调整后放电功率为：%s,柜子信息为:%s,当前时间为:%s,SOC为:%s,当前最小电压为：%s",
//                            activePowerSetting, nowPower, monitorChannelSn, DateUtil.now(), avgSOC, minU));
//                    if ("EWES-230A+".equals(this.emsDeviceModel.getModel())) {
//                        remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlAYT", nowPower / 3, false);
//                        try {
//                            // 防止丢令，休眠1秒
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                        }
//                        remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlBYT", nowPower / 3, false);
//                        try {
//                            // 防止丢令，休眠1秒
//                            Thread.sleep(1500);
//                        } catch (InterruptedException e) {
//                        }
//                        remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlCYT", nowPower / 3, false);
//                    } else {
//                        remoteControlYTVar(monitorChannelSn + "_PCS__ActivePowerSettingYT", nowPower, false);
//                    }
//                }
//            }
//        };
//        executorService.scheduleWithFixedDelay(task, 0L, 30L, TimeUnit.SECONDS);
//    }

    /**
     * 给命令的值必须乘以10的系数，比如想下达10kw，就必须发送100的数值
     *
     * @param varSn
     * @param value
     */
//    private void remoteControlYTVar(String varSn, Double value, boolean retry) {
//
//        Map<String, Object> req = new HashMap<>();
//        req.put("type", "shedian");
//
//        List<Map<String, String>> array = new ArrayList<>();
//        req.put("detail", array);
//
//        Map<String, String> map1 = new HashMap<>();
//        map1.put("varsn", varSn);
//
//        map1.put("value", String.valueOf(value * 10));
//        array.add(map1);
//        /**
//         *  做三次重试机制 如果异常休眠1秒钟
//         */
//        int count = 0;
//        while (count <= 3) {
//            try {
//                HttpRequest.post("http://49.4.81.97:10081/variables/action")
//                        .body(JSONUtil.toJsonStr(req))
//                        .timeout(3000) // 请求等待时间
//                        .execute().body();
//            } catch (Exception e) {
//                if (!retry) {
//                    LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-remoteControlYTVar，请求重试失败，请求参数：%s，异常为->:%s", monitorChannelSn, JSONUtil.toJsonStr(map1), e));
//                    return;
//                }
//                count = count + 1;
//                if (count == 3) {
//                    LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-remoteControlYTVar，collector3次请求重试失败，请求参数：%s，异常为->:%s", monitorChannelSn, JSONUtil.toJsonStr(map1), e));
//                }
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (Exception ec) {
//                }
//                continue;
//            }
//            return;
//        }
//    }


    /**
     * 判断串口打开状态
     *
     * @return
     */
//    public boolean checkSPCStatus() {
//        boolean isOpen = false;
//
//        List<String> list = Arrays.asList(monitorChannelSn + "_SPC__No1YX",
//                monitorChannelSn + "_SPC__No2YX",
//                monitorChannelSn + "_SPC__No7YX",
//                monitorChannelSn + "_SPC__No8YX",
//                monitorChannelSn + "_PCS__SystemOnOffStatus", // 是否启动
//                monitorChannelSn.contains("YXKX_YXKX") ? monitorChannelSn + "_PCS__ControlModeYC" : monitorChannelSn + "_PCS__ControlMode", // 控制模式 230A宜兴科兴 230A/230A+
//                monitorChannelSn + "_PCS__SystemFaultStatus", // PCS故障状态
//                monitorChannelSn + "_PCS__SystemAlarmStatus" // PCS告警状态
//        );
//
//        Map<String, Double> resultTempMap = getResultTempMap(list);
//        if (resultTempMap == null) {
//            resultTempMap = new HashMap<>();
//        }
//        Double SPC_No1 = resultTempMap.get(monitorChannelSn + "_SPC__No1YX");
//        Double SPC_No2 = resultTempMap.get(monitorChannelSn + "_SPC__No2YX");
//        Double SPC_No7 = resultTempMap.get(monitorChannelSn + "_SPC__No7YX");
//        Double SPC_No8 = resultTempMap.get(monitorChannelSn + "_SPC__No8YX");
//        LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s状态:%s", monitorChannelSn + "_SPC__No1YX", SPC_No1));
//        LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s状态:%s", monitorChannelSn + "_SPC__No2YX", SPC_No2));
//        LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s状态:%s", monitorChannelSn + "_SPC__No7YX", SPC_No7));
//        LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s状态:%s", monitorChannelSn + "_SPC__No8YX", SPC_No8));
//        if (Objects.equals(SPC_No1, 1D) &&
//                Objects.equals(SPC_No2, 1D) &&
//                Objects.equals(SPC_No7, 1D) &&
//                Objects.equals(SPC_No8, 1D)) {
//            //所有串口已全部开启，正常执行逻辑
//            // 验证故障或告警，如是则不操作
//            Double systemFaultStatus = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__SystemFaultStatus", 0.0D);
//            Double systemAlarmStatus = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__SystemAlarmStatus", 0.0D);
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s状态:%s", monitorChannelSn + "_PCS__SystemFaultStatus", systemFaultStatus));
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s状态:%s", monitorChannelSn + "_PCS__SystemAlarmStatus", systemAlarmStatus));
//            if (systemFaultStatus > 0D || systemAlarmStatus > 0D) {
//                try {
//                    // 等待休眠60秒
//                    Thread.sleep(1000 * 60);
//                } catch (InterruptedException e) {
//                }
//                return isOpen;
//            }
//
//            // 验证控制模式
//            if ("EWES-230A+".equals(this.emsDeviceModel.getModel())) {
//                // 230A+：状态量
//                Double controlMode = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__ControlMode", 0.0D);
//                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s状态:%s", monitorChannelSn + "_PCS__ControlMode", controlMode));
//                if (controlMode != 1D) {
//                    remoteControlYKVar(monitorChannelSn + "_PCS__ControlModeYK", "1", true);
//                    try {
//                        //设置模式改动后线程休眠10秒
//                        Thread.sleep(1000 * 10);
//                    } catch (InterruptedException e) {
//                    }
//                    return isOpen;
//                }
//            } else {
//                // 230A：模拟量：由于接口为了功率默认乘了倍数，所以需要在这里除去倍数
//                Double controlMode = MapUtils.getDouble(resultTempMap, monitorChannelSn.contains("YXKX_YXKX") ? monitorChannelSn + "_PCS__ControlModeYC" : monitorChannelSn + "_PCS__ControlMode", 0.0D);
//                LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s状态:%s", monitorChannelSn + "_PCS__ControlMode", controlMode));
//                if (controlMode != 2D) {
//                    remoteControlYTVar(monitorChannelSn + "_PCS__ControlModeYT", 0.2, true);
//                    try {
//                        //设置模式改动后线程休眠10秒
//                        Thread.sleep(1000 * 10);
//                    } catch (InterruptedException e) {
//                    }
//                    return isOpen;
//                }
//            }
//
//            //防止存在7号串口处理延迟的问题，每次执行充放电方法时，强制打开7号串口
//            remoteControlYKVar(monitorChannelSn + "_SPC__No7YK", "1", true);
//
//            // 确保pcs在运行中
//            Double pCSSystemOnOffStatus = MapUtils.getDouble(resultTempMap, monitorChannelSn + "_PCS__SystemOnOffStatus", 0.0D);
//            if (!Objects.equals(pCSSystemOnOffStatus, 1D)) {
//                // 未启动，需要开启
//                remoteControlYKVar(monitorChannelSn + "_PCS__StartYK", "1", true);
//                try {
//                    //设置PCS启动后线程休眠60秒
//                    Thread.sleep(1000 * 60);
//                } catch (InterruptedException e) {
//                }
//            } else {
//                isOpen = true;
//            }
//        } else {
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s打开全部串口，60秒后启动PCS", monitorChannelSn));
//            remoteControlYKVar(monitorChannelSn + "_SPC__No1YK", "1", true);
//            remoteControlYKVar(monitorChannelSn + "_SPC__No2YK", "1", true);
//            remoteControlYKVar(monitorChannelSn + "_SPC__No7YK", "1", true);
//            remoteControlYKVar(monitorChannelSn + "_SPC__No8YK", "1", true);
//            try {
//                //设置串口全部打开后线程休眠60秒
//                Thread.sleep(1000 * 60);
//            } catch (Exception e) {
//            }
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s启动PCS，60秒后开始执行充放电策略", monitorChannelSn));
//            remoteControlYKVar(monitorChannelSn + "_PCS__StartYK", "1", true);
//            try {
//                //设置PCS启动后线程休眠60秒
//                Thread.sleep(1000 * 60);
//            } catch (InterruptedException e) {
//            }
//        }
//        return isOpen;
//    }

//    private Map<String, Double> getResultTempMap(List<String> varSns) {
//        List<Object> resultTemp = getResultTemp(varSns);
//        if (resultTemp == null) {
//            return null;
//        }
//        if (resultTemp.size() != varSns.size()) {
//            return null;
//        }
//        Map<String, Double> map = new HashMap<>();
//        for (int i = 0; i < varSns.size(); i++) {
//            map.put(varSns.get(i), ObjectToDouble(resultTemp.get(i)));
//        }
//        return map;
//    }

//    private @NotNull List<Object> getResultTemp(List<String> varSns) {
//
//        return redisTemplate.executePipelined((RedisCallback<List<?>>) connection -> {
//            for (String sn : varSns) {
//                if (StrUtil.isEmpty(sn)) {
//                    continue;
//                }
//                int decollatorPos = sn.lastIndexOf(varDecollator);
//                if (decollatorPos == -1) {
//                    LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("no decollator _ of the key: " + sn));
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
//    }

    /**
     * 远程遥控
     *
     * @param varSn
     * @param value
     */
//    public void remoteControlYKVar(String varSn, String value, boolean retry) {
//        Map<String, Object> req = new HashMap<>();
//        req.put("type", "yaokong-act");
//        List<Map<String, String>> array = new ArrayList<>();
//        req.put("detail", array);
//        Map<String, String> map = new HashMap<>();
//        map.put("varsn", varSn);
//        map.put("value", value);
//        array.add(map);
//
//        /**
//         *  做三次重试机制 如果异常休眠1秒钟
//         */
//        int count = 0;
//        while (count <= 3) {
//            try {
//                HttpRequest.post("http://49.4.81.97:10081/variables/action")
//                        .body(JSONUtil.toJsonStr(req))
//                        .timeout(5000) // 请求等待时间
//                        .execute().body();
//            } catch (Exception e) {
//                if (!retry) {
//                    LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-remoteControlYKVar，请求参数：%s,请求失败，异常为->:%s", monitorChannelSn, JSONUtil.toJsonStr(map), e));
//                    return;
//                }
//                count = count + 1;
//                if (count == 3) {
//                    LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-remoteControlYKVar，collector3次请求重试失败，请求参数：%s ,异常为->:%s", monitorChannelSn, JSONUtil.toJsonStr(map), e));
//                }
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (Exception ec) {
//                }
//                continue;
//            }
//            return;
//        }
//    }


    /**
     * 刷新SOC
     */
//    public void refreshSOC() {
//        Object result = getResult(monitorChannelSn + "_BCMU__SOC");
//        Object fullyChargedStatus = getResult(monitorChannelSn + "_PCS__SystemFullyChargedStatus");
//        Object totallyDischargedStatus = getResult(monitorChannelSn + "_PCS__SystemTotallyDischargedStatus");
//        Double object = ObjectToDouble(result);
//        Double fullyChargedStatusD = ObjectToDouble(fullyChargedStatus);
//        Double totallyDischargedStatusD = ObjectToDouble(totallyDischargedStatus);
//        this.bcmuSoc = object;
//        this.fullCharge = fullyChargedStatusD == 1D;
//        this.fullDischarge = totallyDischargedStatusD == 1D;
//        LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s-SystemFullyChargedStatus->:%s，SystemTotallyDischargedStatus->:%s"
//                , monitorChannelSn, fullyChargedStatusD, totallyDischargedStatusD));
//    }

//    private Double ObjectToDouble(Object o) {
//        return ObjectUtil.isEmpty(o) ? 0 : Double.parseDouble(o.toString());
//    }

//    private Object getResult(String varSn) {
//        int decollatorPos = varSn.lastIndexOf(varDecollator);
//        if (decollatorPos == -1) {
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("no decollator _ of the key: " + varSn));
//            return null;
//        }
//        String energyStorageCabinetSn = varSn.substring(0, decollatorPos);
//        // 截取设备变量sn
//        String varName = varSn.substring(decollatorPos + varDecollator.length());
//        return redisTemplate.opsForHash().get(energyStorageCabinetSn, varName);
//    }

    //关闭串口
    /**
     *
     */
//    Runnable closeSPC = () -> {
//        LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s关闭8号串口", monitorChannelSn));
//        if (isRunning) {
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("正在运行充放电策略，关闭串口失败"));
//        } else {
////            remoteControlYKVar(monitorChannelSn + "_SPC__No7YK","0");
//            remoteControlYKVar(monitorChannelSn + "_SPC__No8YK", "0", false);
//            closeSPCExecutor.shutdown();
//            closeSPCExecutor = Executors.newSingleThreadScheduledExecutor();
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("关闭串口成功"));
//        }
//    };

    /**
     * 获得储能柜通道是否在线
     */
//    public boolean getIsOnline() {
//        boolean isOnline = false;
//
//        // 沟通状态
//        Double communicationStatus = ObjectToDouble(getResult("CD__" + (monitorChannelSn.contains("YXKX_YXKX") ? monitorChannelSn.substring(5) : monitorChannelSn) + "__COMMUNICATION_STATUS"));
//        if (Objects.equals(communicationStatus, 1D)) {
//            isOnline = true;
//        } else {
//            LogTools.log(logger, energyStorageCabinetSn, LogType.INFO, String.format("%s储能柜检测不在线", energyStorageCabinetSn));
//            try {
//                // 等一分钟，防止一直判断
//                Thread.sleep(1000 * 60);
//            } catch (InterruptedException e) {
//            }
//        }
//        return isOnline;
//    }

    /**
     * 获得储能柜是否充满或者是否符合限值
     */
//    public boolean getIsFullChargeOrIsLimitSoc(Integer limitSoc) {
//        if (this.fullCharge || this.bcmuSoc.compareTo(limitSoc.doubleValue()) >= 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * 获得储能柜是否放空或者是否符合限值
     */
//    public boolean getIsFullDischargeOrIsLimitSoc(Integer limitSoc) {
//        if (this.fullDischarge || this.bcmuSoc.compareTo(limitSoc.doubleValue()) <= 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * 设置实际下发功率
     */
//    public void setNowPower(Double nowPower) {
//        this.nowPower = nowPower;
//    }

    /**
     * 获得限值功率值
     *
     * @param isCharge true则根据最高电压，获得充电比值，并限值功率值;false 根据最低电压，获得放电比值，并限值功率值
     */
//    public Double getLimitsPowerValue(boolean isCharge) {
//        // 限值功率值
//        Double limitsPowerValue = 0d;
//        // 额定功率
//        Double power = this.emsDeviceModel.getPowerRating().doubleValue();
//        if (isCharge) {
//            // 充电
//            // BCMU最大电压
//            Double maxU = ObjectToDouble(getResult(monitorChannelSn + "_BCMU__MaxU"));
//            if (maxU >= 3.55 || isAboveTheLimit.get()) {
//                limitsPowerValue = power * 0.35;
//                isAboveTheLimit.set(true);
//            } else {
//                limitsPowerValue = power;
//            }
//        } else {
//            // 放电
//            // BCMU最小电压
//            Double minU = ObjectToDouble(getResult(monitorChannelSn + "_BCMU__MinU"));
//            if (minU < 2.95 || isBelowTheLimit.get()) {
//                limitsPowerValue = power * 0.35;
//                isBelowTheLimit.set(true);
//            } else {
//                limitsPowerValue = power;
//            }
//        }
//        return limitsPowerValue;
//    }

//    public void remove() {
//        if ("EWES-230A+".equals(this.emsDeviceModel.getModel())) {
//            remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlAYT", 0.0, true);
//            try {
//                // 防止丢令，休眠1秒
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlBYT", 0.0, true);
//            try {
//                // 防止丢令，休眠1秒
//                Thread.sleep(1500);
//            } catch (InterruptedException e) {
//            }
//            remoteControlYTVar(monitorChannelSn + "_PCS__SplitPhaseControlCYT", 0.0, true);
//        } else {
//            remoteControlYTVar(monitorChannelSn + "_PCS__ActivePowerSettingYT", 0.0, true);
//        }
//
//        isRunning = false;
//        isStop = true;
//    }
    public void remove() {
        isRunning = false;
        isStop = true;
    }

}
