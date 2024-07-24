package com.example.springbootweb.demos.thread.listener;

//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.util.ObjectUtil;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.eraworks.device.api.entity.*;
//import com.eraworks.device.enums.LogType;
//import com.eraworks.device.service.*;
//import com.eraworks.device.strategy2.constant.StrategyConstantNew;
//import com.eraworks.device.strategy2.thread.StrategyThreadNew;
//import com.eraworks.device.util.LogTools;

import com.example.springbootweb.demos.thread.constant.StrategyConstantNew;
import com.example.springbootweb.demos.thread.thread.StrategyThreadNew;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 充放电策略（新版）
 */
@Slf4j
@Component
public class ChargeDischargeStrategyListenerNew implements ApplicationRunner {

//    // emsdevice服务
//    @Autowired
//    private static EmsDeviceService emsDeviceService;
//
//    @Autowired
//    private static EmsDeviceModelService emsDeviceModelService;
//
//    @Autowired
//    private ChargeDischargeStrategyTemplateService chargeDischargeStrategyTemplateService;
//
//    @Autowired
//    private static ChargeDischargeStrategyTemplateDetailService chargeDischargeStrategyTemplateDetailService;
//
//    @Autowired
//    private ChargeDischargeStrategyConfigurationService chargeDischargeStrategyConfigurationService;
//
//    @Autowired
//    private ChargeDischargeStrategyConfigurationDetailService chargeDischargeStrategyConfigurationDetailService;
//
//    // 充放电历史记录服务
//    @Autowired
//    private static ChargeDischargeStrategyHistoryService chargeDischargeStrategyHistoryService;

    private static Logger logger = LoggerFactory.getLogger(ChargeDischargeStrategyListenerNew.class);

    @Value("${spring.profiles.active}")
    private String active;

    @Resource(name = "strategyThreadPollManager")
    private ThreadPoolExecutor strategyThreadPollManager;

//    @Autowired
//    private RedisTemplate redisTemplate;

    private final static String varDecollator = "__";

//    @Autowired
//    public ChargeDischargeStrategyListenerNew(EmsDeviceService emsDeviceService, EmsDeviceModelService emsDeviceModelService, ChargeDischargeStrategyTemplateDetailService chargeDischargeStrategyTemplateDetailService, ChargeDischargeStrategyHistoryService chargeDischargeStrategyHistoryService) {
//        ChargeDischargeStrategyListenerNew.emsDeviceService = emsDeviceService;
//        ChargeDischargeStrategyListenerNew.emsDeviceModelService = emsDeviceModelService;
//        ChargeDischargeStrategyListenerNew.chargeDischargeStrategyTemplateDetailService = chargeDischargeStrategyTemplateDetailService;
//        ChargeDischargeStrategyListenerNew.chargeDischargeStrategyHistoryService = chargeDischargeStrategyHistoryService;
//    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

//        1. 拿到所有站点的变压器，以及需量
        //        a. 需求：如果是总有功功率为正，则最大需量-正功率为可分配的充电，如果功率为负，则不可多放电
        //        b. 变压器总有功功率，正值还是负值  TS__P
        //        c. 拿到单个变压器下的对应策略模板，已经对应的策略明细表
        //        d. 拿到单个变压器下的对应储能柜，以及对应的型号等信息
        //        e. 根据策略模板统计下面的储能柜数量，统计此时储能柜整体是正还是负，进行分配功率，放在全局 map 中
        //        f. 外部控制可控制是否关闭相应的储能柜线程，比如通过 this.stop 等方式
        //        g. 内部储能柜
        //        ⅰ. 根据从全局 map 中拿到的 分配后的功率进行充放电，如果到点了，充满，放空了，则关闭，自己管自己

//        if (Objects.equals(active, "prod")) {
//
//            // 所有策略模板
//            List<ChargeDischargeStrategyTemplate> chargeDischargeStrategyTemplateList = chargeDischargeStrategyTemplateService.list();
//            // 策略模板下塞入明细
//            List<ChargeDischargeStrategyTemplateDetail> chargeDischargeStrategyTemplateDetailList = chargeDischargeStrategyTemplateDetailService.list();
//
//            // 合并成map,策略模板id对应明细
//            Map<Integer, List<ChargeDischargeStrategyTemplateDetail>> template_detail = null;
//            if (CollUtil.isNotEmpty(chargeDischargeStrategyTemplateList) && CollUtil.isNotEmpty(chargeDischargeStrategyTemplateDetailList)) {
//                template_detail = chargeDischargeStrategyTemplateDetailList.stream().collect(Collectors.groupingBy(ChargeDischargeStrategyTemplateDetail::getCsdId));
//            }
//            if (template_detail == null) {
//                // 无策略，直接停止
//                return;
//            }
//            // 更新2：模板id和明细
//            StrategyConstantNew.STRATEGY_TEMPLATE_DETAIL = new ConcurrentHashMap<>(template_detail);
//
//
//            // 所有变压器，拿到最大需量
//            List<EmsDevice> transformerList = emsDeviceService.list(Wrappers.<EmsDevice>query().lambda().
//                    eq(EmsDevice::getProductTypeId, 7) // 变压器
//            );
//
//            // 更新1：最大需量
//            if (CollUtil.isNotEmpty(transformerList)) {
//                for (EmsDevice emsDevice : transformerList) {
//                    StrategyConstantNew.STRATEGY_MAXDEMAND.put(emsDevice.getProductDeviceSn(), emsDevice.getMaxDemand());
//                    // 通道号
//                    String[] transformerSnArray = emsDevice.getProductDeviceSn().split("_");
//                    String monitorChannelSn = transformerSnArray[0] + "_" + transformerSnArray[1];
//                    StrategyConstantNew.STRATEGY_MONITORCHANNELSN.put(emsDevice.getProductDeviceSn(), monitorChannelSn);
//
//                }
//            }
//
//            // 更新9：动力柜sn
//            List<EmsDevice> powerCabinetList = emsDeviceService.list(Wrappers.<EmsDevice>query().lambda().
//                    eq(EmsDevice::getProductTypeId, 8) // 动力柜
//            );
//
//            if (CollUtil.isNotEmpty(transformerList) && CollUtil.isNotEmpty(powerCabinetList)) {
//                for (EmsDevice transformerDevice : transformerList) {
//                    List<String> powerCabinetSnList = new ArrayList<>();
//                    for (EmsDevice powerCabinetSnDevice : powerCabinetList) {
//                        if (transformerDevice.getId().equals(powerCabinetSnDevice.getParentId())) {
//                            powerCabinetSnList.add(powerCabinetSnDevice.getProductDeviceSn());
//                        }
//                    }
//                    StrategyConstantNew.STRATEGY_POWER_CABINET_SN.put(transformerDevice.getProductDeviceSn(), powerCabinetSnList);
//                }
//            }
//
//            // 所有储能柜
//            List<EmsDevice> energyStorageCabinetList = emsDeviceService.list(Wrappers.<EmsDevice>query().lambda().
//                    eq(EmsDevice::getProductTypeId, 1) // 储能柜
//            );
//
//            // 所有储能柜型号
//            List<EmsDeviceModel> energyStorageCabinetModelList = emsDeviceModelService.list();
//
//            // 更新6:储能柜真实sn 对应 储能柜型号，功率，额定容量等
//            // 更新7:储能柜真实sn 对应 储能柜拓扑sn
//            for (EmsDevice emsDevice : energyStorageCabinetList) {
//                EmsDeviceModel emsDeviceModel = energyStorageCabinetModelList.stream().filter(e -> Objects.equals(e.getId(), emsDevice.getEmsDeviceModelId())).findFirst().get();
//                // 储能柜型号
//                StrategyConstantNew.STRATEGY_CABINET_SN_EMSDEVICEMODEL.put(emsDevice.getEnergyStorageCabinetSn(), emsDeviceModel);
//
//                // 储能柜拓扑sn
//                StrategyConstantNew.STRATEGY_CABINET_SN_PRODUCT_DEVICE_SN.put(emsDevice.getEnergyStorageCabinetSn(), emsDevice.getProductDeviceSn());
//            }
//
//
//            // 所有充放电策略配置 -> 变压器对应多个充放电策略模板
//            List<ChargeDischargeStrategyConfiguration> chargeDischargeStrategyConfigurationList = chargeDischargeStrategyConfigurationService.list();
//            if (CollUtil.isEmpty(chargeDischargeStrategyConfigurationList)) {
//                return;
//            }
//            // Map<变压器sn，多个充放电策略模板List
//            Map<String, List<Integer>> strategyTemplateMap = new HashMap<>();
//            Map<String, List<ChargeDischargeStrategyConfiguration>> chargeDischargeStrategyConfigurationlistMap = chargeDischargeStrategyConfigurationList.stream().collect(Collectors.groupingBy(ChargeDischargeStrategyConfiguration::getTransformerSn));
//            for (String index : chargeDischargeStrategyConfigurationlistMap.keySet()) {
//                List<ChargeDischargeStrategyConfiguration> list = chargeDischargeStrategyConfigurationlistMap.get(index);
//                List<Integer> templateIdList = new ArrayList<>();
//                if (CollUtil.isNotEmpty(list)) {
//                    for (ChargeDischargeStrategyConfiguration chargeDischargeStrategyConfiguration : list) {
//                        templateIdList.add(chargeDischargeStrategyConfiguration.getChargeDischargeStrategyTemplateId());
//                    }
//                }
//                if (CollUtil.isNotEmpty(templateIdList)) {
//                    strategyTemplateMap.put(index, templateIdList);
//                }
//            }
//            if (CollUtil.isEmpty(strategyTemplateMap)) {
//                return;
//            }
//            // 更新3：变压器sn，策略模板id
//            StrategyConstantNew.STRATEGY_TEMPLATE_ID = new ConcurrentHashMap<>(strategyTemplateMap);
//            // 所有充放电策略配置明细 -> 充放电策略模板下的多个储能柜
//            List<ChargeDischargeStrategyConfigurationDetail> chargeDischargeStrategyConfigurationDetailList = chargeDischargeStrategyConfigurationDetailService.list();
//
//            // 根据充放电策略配置进行整理，并创建线程，线程内部根据策略来进行充放电
//            if (CollUtil.isNotEmpty(chargeDischargeStrategyConfigurationList) && CollUtil.isNotEmpty(chargeDischargeStrategyConfigurationDetailList)) {
//                // 分组
//                Map<Integer, List<ChargeDischargeStrategyConfigurationDetail>> collect = chargeDischargeStrategyConfigurationDetailList.stream().collect(Collectors.groupingBy(ChargeDischargeStrategyConfigurationDetail::getChargeDischargeStrategyConfigurationId));
//                // 变压器id为核心
//                for (ChargeDischargeStrategyConfiguration chargeDischargeStrategyConfiguration : chargeDischargeStrategyConfigurationList) {
//                    Integer id = chargeDischargeStrategyConfiguration.getId();
//                    List<ChargeDischargeStrategyConfigurationDetail> list = collect.get(id);
//                    if (CollUtil.isEmpty(list)) {
//                        continue;
//                    }
//                    // 配置组id和配置对象中id对应
//                    Integer chargeDischargeStrategyTemplateId = chargeDischargeStrategyConfiguration.getChargeDischargeStrategyTemplateId();
//
//                    // 策略模板id对应储能柜List
//                    List<String> cabinetSnList = new ArrayList<>();
//                    for (ChargeDischargeStrategyConfigurationDetail chargeDischargeStrategyConfigurationDetail : list) {
//                        cabinetSnList.add(chargeDischargeStrategyConfigurationDetail.getEnergyStorageCabinetSn());
//                        // 判断是否在线
//                        boolean isOnline = this.getIsOnline(StrategyConstantNew.STRATEGY_CABINET_SN_PRODUCT_DEVICE_SN.get(chargeDischargeStrategyConfigurationDetail.getEnergyStorageCabinetSn()));
//                        if (isOnline) {
//                            // 在线
//                            // 创建子线程
//                            StrategyThreadNew strategyThreadNew = new StrategyThreadNew(redisTemplate, chargeDischargeStrategyConfiguration.getTransformerSn(), chargeDischargeStrategyTemplateId, chargeDischargeStrategyConfigurationDetail.getEnergyStorageCabinetSn());
//                            strategyThreadPollManager.submit(strategyThreadNew);
//                            // 更新5：储能柜真实sn 对应 线程
//                            StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.put(chargeDischargeStrategyConfigurationDetail.getEnergyStorageCabinetSn(), strategyThreadNew);
//                            continue;
//                        }
//                        // 离线
//                    }
//                    // 更新4：策略模板id对应储能柜真实sn list
//                    StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.put(chargeDischargeStrategyTemplateId, cabinetSnList);
//                }
//            }
//
//            // 创建总线程，定时扫描判断执行
//            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//            Runnable task = () -> {
//                // 拿到全局最大需量，与充放策略，与储能柜，动态分配功率
//                // 默认都是固定的。不是残留的线程
////            a. 如果更新变压器(改动sn)，则提示会自动关闭所有附属储能柜执行，直接关闭该储能柜线程
////            b. 如果更新变压器最大需量，则不要紧，程序更新全局最大需量
////                如果更新变压器名称，也不要紧
////            c. 如果更新策略模板(改动明细)，则提示会自动关闭所有附属储能柜执行，则尽量不直接关闭储能柜线程，如果线程中有明细内容需要重新获取并比较
////              如果更新策略名称这些，也不要紧
////            d. 如果更新储能柜，则提示会自动关闭所有附属储能柜执行，关闭被删除的储能柜，程序自动扫描生成新储能柜
//
//                // 默认全局变量都是最新的，根据最大需量和同一时刻变压器功率分配(同一时刻多策略，策略明细下多储能柜)充放电功率
//                // 实时时间
//                LocalDateTime now = LocalDateTime.now();
//
//                // 先整理统计
//                if (CollUtil.isEmpty(StrategyConstantNew.STRATEGY_MONITORCHANNELSN)) {
//                    // 如果没有变压器则不处理
//                    return;
//                }
//
//                for (String transformerSn : StrategyConstantNew.STRATEGY_MONITORCHANNELSN.keySet()) {
//                    // 该充电总功率
//                    Double chargeAllPower = 0d;
//                    // 该放电总功率
//                    Double disChargeAllPower = 0d;
//                    // 充电储能柜
//                    List<String> chargeAllEnergyStorageCabinet = new ArrayList<>();
//                    // 放电储能柜
//                    List<String> disChargeAllEnergyStorageCabinet = new ArrayList<>();
//                    // 离线储能柜
//                    List<String> offLineAllEnergyStorageCabinet = new ArrayList<>();
//                    // 充电储能柜实际下发功率
//                    Map<String, Double> nowChargePowerMap = new HashMap<>();
//                    // 放电储能柜实际下发功率
//                    Map<String, Double> nowDisChargePowerMap = new HashMap<>();
//
//                    // 通道sn
//                    String monitorChannelSn = StrategyConstantNew.STRATEGY_MONITORCHANNELSN.get(transformerSn);
//                    // 最大需量
//                    Integer maxDemand = StrategyConstantNew.STRATEGY_MAXDEMAND.get(transformerSn);
//                    // 变压器下策略模板id
//                    List<Integer> templateIdList = StrategyConstantNew.STRATEGY_TEMPLATE_ID.get(transformerSn);
//                    // 获得模板id下的模板明细
//                    // 同时 获得模板id下的储能柜
//                    // 同时 获得相应的储能柜线程
//                    if (CollUtil.isNotEmpty(templateIdList)) {
//                        for (Integer templateId : templateIdList) {
//                            List<ChargeDischargeStrategyTemplateDetail> chargeDischargeStrategyTemplateDetailListNew = StrategyConstantNew.STRATEGY_TEMPLATE_DETAIL.get(templateId);
//                            for (ChargeDischargeStrategyTemplateDetail detail : chargeDischargeStrategyTemplateDetailListNew) {
//                                LocalDateTime startTime = detail.getStartTime();
//                                LocalDateTime endTime = detail.getEndTime();
//                                LocalDateTime strategyStart = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), startTime.getHour(), startTime.getMinute());
//                                LocalDateTime strategyEnd = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), endTime.getHour(), endTime.getMinute());
//                                Integer limitSoc = detail.getLimitSoc();
//
//                                if (now.isAfter(strategyStart) && now.isBefore(strategyEnd)) {
//
//                                    if (CollUtil.isEmpty(StrategyConstantNew.STRATEGY_TEMPLATE_CABINET) || CollUtil.isEmpty(StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(templateId))) {
//                                        // 如果为空则不处理
//                                        continue;
//                                    }
//                                    switch (detail.getStrategyType()) {
//                                        case 0:
//                                            //充电
//                                            // 储能柜线程判断是否在线
//                                            // 储能柜线程判断是否充满
//                                            // 储能柜线程获得额定功率的比值
//                                            // 根据比值和额定功率获得功率限值
//                                            // 根据功率限值，分配功率 两者比较绝对值的小值来累计--做实际下发变量记录
//                                            // 最后要平均!!!：实际下发1+实际下发2+...=x，实际下发/x*100%=y，最后总充功率*y为该储能柜的充功率，但是如果充满/掉线，则比值为0，最后的充功率也为0
//
//
//                                            // 所有充电储能柜
////                                        chargeAllEnergyStorageCabinet.addAll(StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(templateId));
//
//                                            for (String energyStorageCabinetSn : StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(templateId)) {
//                                                StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
//
//                                                // 判断是否在线
//                                                boolean isOnline = this.getIsOnline(StrategyConstantNew.STRATEGY_CABINET_SN_PRODUCT_DEVICE_SN.get(energyStorageCabinetSn));
//
//                                                if (strategyThreadNew != null) {
//                                                    Double tempPower = 0d;
//
//                                                    if (isOnline) {
//                                                        // 在线
//                                                        chargeAllEnergyStorageCabinet.add(energyStorageCabinetSn);
//
//                                                        // 是否充满或者是否符合限值
//                                                        Boolean isFullCharge = strategyThreadNew.getIsFullChargeOrIsLimitSoc(limitSoc);
//                                                        if (isFullCharge) {
//                                                            tempPower = 0d;
//                                                        } else {
//                                                            // 未充满
//                                                            // 功率比值+型号/额定功率 得到功率限值
//                                                            Double limitsPowerValue = strategyThreadNew.getLimitsPowerValue(true);
//                                                            // 比较绝对值大小，取小的原值
//                                                            if (Math.abs(limitsPowerValue) > Math.abs(detail.getPower())) {
//                                                                tempPower = detail.getPower();
//                                                            } else {
//                                                                tempPower = limitsPowerValue;
//                                                            }
//                                                        }
//                                                        // 记录 储能柜实际sn：比较的结果值
//                                                        nowChargePowerMap.put(energyStorageCabinetSn, tempPower);
//                                                        // 累计
//                                                        chargeAllPower = chargeAllPower + tempPower;
//
//                                                    } else {
//                                                        // 离线
//                                                        offLineAllEnergyStorageCabinet.add(energyStorageCabinetSn);
//                                                    }
//                                                } else {
//                                                    // 需要创建
//                                                    if (isOnline) {
//                                                        // 在线
//                                                        chargeAllEnergyStorageCabinet.add(energyStorageCabinetSn);
//
//                                                        // 默认该储能柜根据配置满功率，忽略掉型号问题带来的判断
//                                                        nowChargePowerMap.put(energyStorageCabinetSn, detail.getPower());
//                                                        // 累计
//                                                        chargeAllPower = chargeAllPower + detail.getPower();
//                                                    } else {
//                                                        // 离线
//                                                        offLineAllEnergyStorageCabinet.add(energyStorageCabinetSn);
//                                                    }
//                                                }
//
//                                            }
//                                            break;
//                                        case 1:
//                                            //放电
//                                            // 参照上面充电
//
//                                            // 所有充电储能柜
////                                        disChargeAllEnergyStorageCabinet.addAll(StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(templateId));
//
//                                            for (String energyStorageCabinetSn : StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(templateId)) {
//                                                StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
//
//                                                // 判断是否在线
//                                                boolean isOnline = this.getIsOnline(StrategyConstantNew.STRATEGY_CABINET_SN_PRODUCT_DEVICE_SN.get(energyStorageCabinetSn));
//
//                                                if (strategyThreadNew != null) {
//                                                    Double tempPower = 0d;
//
//                                                    if (isOnline) {
//                                                        // 在线
//                                                        disChargeAllEnergyStorageCabinet.add(energyStorageCabinetSn);
//
//                                                        // 是否放空或者是否符合限值
//                                                        Boolean isFullDisCharge = strategyThreadNew.getIsFullDischargeOrIsLimitSoc(limitSoc);
//                                                        if (isFullDisCharge) {
//                                                            tempPower = 0d;
//                                                        } else {
//                                                            // 未放空
//                                                            // 功率比值+型号/额定功率 得到功率限值
//                                                            Double limitsPowerValue = strategyThreadNew.getLimitsPowerValue(false);
//                                                            // 取负值
//                                                            limitsPowerValue = -limitsPowerValue;
//                                                            // 比较绝对值大小，取小的原值
//                                                            if (Math.abs(limitsPowerValue) > Math.abs(detail.getPower())) {
//                                                                tempPower = detail.getPower();
//                                                            } else {
//                                                                tempPower = limitsPowerValue;
//                                                            }
//                                                        }
//                                                        // 记录 储能柜实际sn：比较的结果值
//                                                        nowDisChargePowerMap.put(energyStorageCabinetSn, tempPower);
//                                                        // 累计
//                                                        disChargeAllPower = disChargeAllPower + tempPower;
//                                                    } else {
//                                                        // 离线
//                                                        offLineAllEnergyStorageCabinet.add(energyStorageCabinetSn);
//                                                    }
//                                                } else {
//                                                    // 需要创建
//                                                    if (isOnline) {
//                                                        // 在线
//                                                        disChargeAllEnergyStorageCabinet.add(energyStorageCabinetSn);
//
//                                                        // 默认该储能柜根据配置满功率，忽略掉型号问题带来的判断
//                                                        nowDisChargePowerMap.put(energyStorageCabinetSn, detail.getPower());
//                                                        // 累计
//                                                        disChargeAllPower = disChargeAllPower + detail.getPower();
//                                                    } else {
//                                                        // 离线
//                                                        offLineAllEnergyStorageCabinet.add(energyStorageCabinetSn);
//                                                    }
//                                                }
//
//                                            }
//                                            break;
//                                        default:
//                                            break;
//                                    }
//                                }
//
//                            }
//
//                        }
//                    }
//
//                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("变压器:%s，充电总功率:%s，放电总功率:%s，充电储能柜:%s，放电储能柜:%s，离线储能柜：%s", transformerSn, chargeAllPower, disChargeAllPower, chargeAllEnergyStorageCabinet.toString(), disChargeAllEnergyStorageCabinet.toString(), offLineAllEnergyStorageCabinet.toString()));
//                    // 日志
////                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("STRATEGY_MONITORCHANNELSN:%s", StrategyConstantNew.STRATEGY_MONITORCHANNELSN.toString()));
////                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("STRATEGY_MAXDEMAND:%s", StrategyConstantNew.STRATEGY_MAXDEMAND.toString()));
////                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("STRATEGY_TEMPLATE_DETAIL:%s", StrategyConstantNew.STRATEGY_TEMPLATE_DETAIL.toString()));
////                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("STRATEGY_TEMPLATE_ID:%s", StrategyConstantNew.STRATEGY_TEMPLATE_ID.toString()));
////                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("STRATEGY_TEMPLATE_CABINET:%s", StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.toString()));
////                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("STRATEGY_CABINET_SN_THREAD:%s", StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.toString()));
////                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("STRATEGY_CABINET_SN_EMSDEVICEMODEL:%s", StrategyConstantNew.STRATEGY_CABINET_SN_EMSDEVICEMODEL.toString()));
////                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("STRATEGY_CABINET_SN_PRODUCT_DEVICE_SN:%s", StrategyConstantNew.STRATEGY_CABINET_SN_PRODUCT_DEVICE_SN.toString()));
//
//
//                    // 根据整理统计结果进行功率分配
//                    // 变压器功率
//                    Object ts__p = getResult(monitorChannelSn + "_TS__P");
//                    Double aDouble = ObjectToDouble(ts__p);
//
//                    // 动力柜功率
//                    List<String> powerCabinetSnList = StrategyConstantNew.STRATEGY_POWER_CABINET_SN.get(transformerSn);
//                    if (CollUtil.isEmpty(powerCabinetSnList)) {
//                        // 如果没有配置动力柜则不处理
//                        continue;
//                    }
//                    Double eDouble = 0D;
//                    for (String powerCabinetSn : powerCabinetSnList) {
//                        LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 动力柜SN:%s", powerCabinetSn));
//                        Object em__p = getResult(powerCabinetSn + "__P");
//                        eDouble = eDouble + ObjectToDouble(em__p);
//                    }
//
//                    // 实际总负载
//                    Double realP = aDouble - eDouble;
//                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 变压器功率:%s,动力柜功率:%s，实际总负载:%s", aDouble, eDouble, realP));
//
//
//                    // 需量，实时功率，充放电总功率，实际充放电总功率
//                    // 需量和充放电总功率不变，实时功率和实际充放电总功率变化，实际充放电总功率根据上述三个参数进行变化
//                    // 直接绕过乱七八糟的动力柜，直接用储能柜功率比较
//                    // 核心需求：1.充电不可超过需量，2.放电不可浪费电
//
//                    // 实际下发总充电功率
//                    Double nowChargeAllPower = 0d;
//                    // 实际下发总放电功率
//                    Double nowDisChargeAllPower = 0d;
//
//                    // 充等于放
//                    // 1. 查看充是否超过需量，如超过需要减少充
//                    if (chargeAllPower + realP > maxDemand) {
//                        Double difference = chargeAllPower + realP - maxDemand;
//                        nowChargeAllPower = chargeAllPower - difference;
//                        if (nowChargeAllPower < 0d) {
//                            nowChargeAllPower = 0d;
//                        }
//                    } else {
//                        nowChargeAllPower = chargeAllPower;
//                    }
//                    // 2.查看整个变压器功率是否小于0，如超过需要减少放
//                    if (chargeAllPower + disChargeAllPower + realP < 0) {
//                        Double difference = chargeAllPower + disChargeAllPower + realP;
//                        nowDisChargeAllPower = disChargeAllPower - difference;
//                        if (nowDisChargeAllPower > 0d) {
//                            nowDisChargeAllPower = 0d;
//                        }
//                    } else {
//                        nowDisChargeAllPower = disChargeAllPower;
//                    }
//
//                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 nowChargeAllPower:%s", nowChargeAllPower));
//                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 nowDisChargeAllPower:%s", nowDisChargeAllPower));
//
//
//                    // 特殊：需要考虑电压过高，过低，低充低放的情况，以及充满，放空的情况--逻辑写在上面-已完成
//                    // 实际分配的功率：充电
//                    for (String energyStorageCabinetSn : chargeAllEnergyStorageCabinet) {
//                        Double nowPower = nowChargePowerMap.get(energyStorageCabinetSn);
//                        LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 充电 energyStorageCabinetSn:%s,nowPower:%s,chargeAllPower:%s,nowChargeAllPower:%s", energyStorageCabinetSn, nowPower, chargeAllPower, nowChargeAllPower));
//                        if (chargeAllPower.equals(0d)) {
//                            nowPower = 0d;
//                        } else {
//                            nowPower = nowPower / chargeAllPower * nowChargeAllPower;
//                        }
//                        LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 充电 energyStorageCabinetSn:%s,nowPower新:%s", energyStorageCabinetSn, nowPower));
//
//                        // 下发储能柜线程
//                        StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
//                        if (strategyThreadNew != null) {
//                            LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 充电线程 strategyThreadNew 非空:%s", strategyThreadNew));
//                            strategyThreadNew.setNowPower(nowPower);
//                        } else {
//                            LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 充电线程 strategyThreadNew 空:%s", strategyThreadNew));
//
//                            LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 充电线程 strategyThreadNew 空:%s,STRATEGY_TEMPLATE_CABINET:%s", strategyThreadNew, StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.toString()));
//
//                            // 创建子线程
//                            // 拿到策略模板id
//                            Integer chargeDischargeStrategyTemplateId = 0;
//                            for (Integer tempChargeDischargeStrategyTemplateId : StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.keySet()) {
//                                List<String> energyStorageCabinetSnList = StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(tempChargeDischargeStrategyTemplateId);
//                                if (energyStorageCabinetSnList.stream().anyMatch(e -> e.equals(energyStorageCabinetSn))) {
//                                    chargeDischargeStrategyTemplateId = tempChargeDischargeStrategyTemplateId;
//                                    break;
//                                }
//                            }
//                            LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 充电 energyStorageCabinetSn:%s,chargeDischargeStrategyTemplateId:%s,transformerSn:%s", energyStorageCabinetSn, chargeDischargeStrategyTemplateId, transformerSn));
//
//                            strategyThreadNew = new StrategyThreadNew(redisTemplate, transformerSn, chargeDischargeStrategyTemplateId, energyStorageCabinetSn);
//                            strategyThreadNew.setNowPower(nowPower);
//                            strategyThreadPollManager.submit(strategyThreadNew);
//                            // 更新5：储能柜真实sn 对应 线程
//                            StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.put(energyStorageCabinetSn, strategyThreadNew);
//                            LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 充电 线程提交成功 energyStorageCabinetSn:%s", energyStorageCabinetSn));
//
//                        }
//                    }
//                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 充电成功:%s", chargeAllEnergyStorageCabinet.toString()));
//
//                    // 实际分配的功率：放电
//                    for (String energyStorageCabinetSn : disChargeAllEnergyStorageCabinet) {
//                        Double nowPower = nowDisChargePowerMap.get(energyStorageCabinetSn);
//                        LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 放电 energyStorageCabinetSn:%s,nowPower:%s,disChargeAllPower:%s,nowDisChargeAllPower:%s", energyStorageCabinetSn, nowPower, disChargeAllPower, nowDisChargeAllPower));
//                        if (disChargeAllPower.equals(0d)) {
//                            nowPower = 0d;
//                        } else {
//                            nowPower = nowPower / disChargeAllPower * nowDisChargeAllPower;
//                        }
//                        LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 放电 energyStorageCabinetSn:%s,nowPower新:%s", energyStorageCabinetSn, nowPower));
//
//                        // 下发储能柜线程
//                        StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
//                        if (strategyThreadNew != null) {
//                            strategyThreadNew.setNowPower(nowPower);
//                        } else {
//                            // 创建子线程
//                            // 拿到策略模板id
//                            Integer chargeDischargeStrategyTemplateId = 0;
//                            for (Integer tempChargeDischargeStrategyTemplateId : StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.keySet()) {
//                                List<String> energyStorageCabinetSnList = StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(tempChargeDischargeStrategyTemplateId);
//                                if (energyStorageCabinetSnList.stream().anyMatch(e -> e.equals(energyStorageCabinetSn))) {
//                                    chargeDischargeStrategyTemplateId = tempChargeDischargeStrategyTemplateId;
//                                    break;
//                                }
//
//                            }
//                            LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 放电线程 energyStorageCabinetSn:%s,chargeDischargeStrategyTemplateId:%s,transformerSn:%s", energyStorageCabinetSn, chargeDischargeStrategyTemplateId, transformerSn));
//
//                            strategyThreadNew = new StrategyThreadNew(redisTemplate, transformerSn, chargeDischargeStrategyTemplateId, energyStorageCabinetSn);
//                            strategyThreadNew.setNowPower(nowPower);
//                            strategyThreadPollManager.submit(strategyThreadNew);
//                            // 更新5：储能柜真实sn 对应 线程
//                            StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.put(energyStorageCabinetSn, strategyThreadNew);
//
//                            LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 放电 线程提交成功 energyStorageCabinetSn:%s", energyStorageCabinetSn));
//
//                        }
//                    }
//                    LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("增加监视日志 放电成功:%s", disChargeAllEnergyStorageCabinet.toString()));
//
//
//                }
//            };
//
//
//            // 30s循环执行一次
//            executorService.scheduleWithFixedDelay(task, 0L, 30L, TimeUnit.SECONDS);
//
//        }

        List<String> energyStorageCabinetSnList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            energyStorageCabinetSnList.add("EMS-" + i);
        }

        StrategyConstantNew.STRATEGY_POWER_CABINET_SN.put("EMS", energyStorageCabinetSnList);


        // 创建总线程，定时扫描判断执行
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {

            List<String> energyStorageCabinetSnList_new = StrategyConstantNew.STRATEGY_POWER_CABINET_SN.get("EMS");
            for (String energyStorageCabinetSn : energyStorageCabinetSnList_new) {

                StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
                if (strategyThreadNew != null) {
                    log.error("线程存在:{}", energyStorageCabinetSn);
                } else {
                    strategyThreadNew = new StrategyThreadNew(energyStorageCabinetSn);
//                strategyThreadNew.setNowPower(nowPower);
                    strategyThreadPollManager.submit(strategyThreadNew);

                    StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.put(energyStorageCabinetSn, strategyThreadNew);

                }

                log.error("增加监视日志 测试成功:{}", energyStorageCabinetSn);
            }


        };

        executorService.scheduleWithFixedDelay(task, 0L, 30L, TimeUnit.SECONDS);
    }

    /**
     * 增加
     */
    public static void add() {

    }

    /**
     * 编辑
     */
    public void edit() {

    }

    /**
     * 停止
     */
    public static void stop() {

    }

    /**
     * 移除
     */
    public void remove() {
    }

    /**
     * 暂停
     */
    public void pause() {

    }


    /**
     * 获得redis最新值
     *
     * @param varSn 变量sn码
     * @return
     */
//    private Object getResult(String varSn) {
//        int decollatorPos = varSn.lastIndexOf(varDecollator);
//        if (decollatorPos == -1) {
//            LogTools.log(logger, "ChargeDischargeStrategyListenerNew", LogType.INFO, String.format("no decollator _ of the key: " + varSn));
//            return null;
//        }
//        String deviceSn = varSn.substring(0, decollatorPos);
//        // 截取设备变量sn
//        String varName = varSn.substring(decollatorPos + varDecollator.length());
//        return redisTemplate.opsForHash().get(deviceSn, varName);
//    }

    /**
     * 转double
     *
     * @param o
     * @return
     */
//    private Double ObjectToDouble(Object o) {
//        return ObjectUtil.isEmpty(o) ? 0 : Double.parseDouble(o.toString());
//    }


    // 全局新增变压器信息：最大需量/通道号
//    public static void saveTransformerInfo(String transformerSn, Integer maxDemand) {
//        StrategyConstantNew.STRATEGY_MAXDEMAND.put(transformerSn, maxDemand);
//
//        // 通道号
//        String[] transformerSnArray = transformerSn.split("_");
//        String monitorChannelSn = transformerSnArray[0] + "_" + transformerSnArray[1];
//        StrategyConstantNew.STRATEGY_MONITORCHANNELSN.put(transformerSn, monitorChannelSn);
//    }

    // 全局更新策略模板明细
//    public static void updateStrategyTemplateDetail(Integer templateId, Integer createId, String historyString) {
//        if (CollUtil.isNotEmpty(StrategyConstantNew.STRATEGY_TEMPLATE_DETAIL.get(templateId))) {
//            // 策略模板下塞入明细
//            List<ChargeDischargeStrategyTemplateDetail> chargeDischargeStrategyTemplateDetailList = chargeDischargeStrategyTemplateDetailService.list(new QueryWrapper<ChargeDischargeStrategyTemplateDetail>().eq("csd_id", templateId));
//
//            StrategyConstantNew.STRATEGY_TEMPLATE_DETAIL.put(templateId, chargeDischargeStrategyTemplateDetailList);
//
//            // 关联的线程全部关闭，重新来
//            // 储能柜真实sn list
//            List<String> energyStorageCabinetSnlist = StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(templateId);
//            if (CollUtil.isNotEmpty(energyStorageCabinetSnlist)) {
//                LocalDateTime now = LocalDateTime.now();
//
//                for (String energyStorageCabinetSn : energyStorageCabinetSnlist) {
//                    // 线程：必须清除
//                    StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
//                    if (strategyThreadNew != null) {
//                        strategyThreadNew.remove();
//                    }
//                    StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.remove(energyStorageCabinetSn);
//
//                    // 型号：可以不清除
//                    // sn：可以不清除
//
//                    // 新增更新历史记录
//                    ChargeDischargeStrategyHistory chargeDischargeStrategyHistory = new ChargeDischargeStrategyHistory();
//                    // 根据储能柜真实sn查询到设备信息
//                    EmsDevice emsDevice = emsDeviceService.getOne(new QueryWrapper<EmsDevice>().eq("energy_storage_cabinet_sn", energyStorageCabinetSn));
//                    chargeDischargeStrategyHistory.setDeviceName(emsDevice.getName());
//                    chargeDischargeStrategyHistory.setDeviceSn(energyStorageCabinetSn);
//                    chargeDischargeStrategyHistory.setStationSn(emsDevice.getStationSn());
//                    chargeDischargeStrategyHistory.setCreateBy(createId.toString());
//                    chargeDischargeStrategyHistory.setCreateTime(now);
//                    chargeDischargeStrategyHistory.setSource(ChargeDischargeStrategyHistory.StrategySource.TRANSFORMER_WHOLE.getSourceType());
//                    chargeDischargeStrategyHistory.setDetail(historyString);
//                    chargeDischargeStrategyHistoryService.save(chargeDischargeStrategyHistory);
//                }
//            }
//        }
//
//    }

    // 删除变压器下策略模板的明细
//    public static void delStrategyTemplateDetail(Integer templateId, ChargeDischargeStrategyTemplateDetail chargeDischargeStrategyTemplateDetail) {
//        List<ChargeDischargeStrategyTemplateDetail> list = StrategyConstantNew.STRATEGY_TEMPLATE_DETAIL.get(templateId);
//        if (CollUtil.isNotEmpty(list)) {
//            list.removeIf(detail -> detail.getId().equals(chargeDischargeStrategyTemplateDetail.getId()));
//        }
//        StrategyConstantNew.STRATEGY_TEMPLATE_DETAIL.put(templateId, list);
//
//        // 关联的线程全部停止，重新来
//        // 储能柜真实sn list
//        List<String> energyStorageCabinetSnlist = StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(templateId);
//        if (CollUtil.isNotEmpty(energyStorageCabinetSnlist)) {
//            for (String energyStorageCabinetSn : energyStorageCabinetSnlist) {
//                // 线程：必须清除
//                StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
//                if (strategyThreadNew != null) {
//                    strategyThreadNew.remove();
//                }
//                StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.remove(energyStorageCabinetSn);
//
//                // 型号：可以不清除
//                // sn：可以不清除
//            }
//        }
//
//    }

    // 新增变压器sn和策略模板id的对应
//    public static void addTransformerSnTemplateId(String transformerSn, Integer templateId) {
//        List<Integer> templateIdList = StrategyConstantNew.STRATEGY_TEMPLATE_ID.get(transformerSn);
//        if (CollUtil.isEmpty(templateIdList)) {
//            templateIdList = new ArrayList<>();
//        }
//        templateIdList.add(templateId);
//
//        StrategyConstantNew.STRATEGY_TEMPLATE_ID.put(transformerSn, templateIdList);
//    }

    // 删除变压器sn和策略模板id的对应
//    public static void delTransformerSnTemplateId(String transformerSn, Integer templateId, Integer createId, String historyString) {
//
//        List<Integer> templateIdList = StrategyConstantNew.STRATEGY_TEMPLATE_ID.get(transformerSn);
//        if (CollUtil.isNotEmpty(templateIdList)) {
//            // 储能柜真实sn list
//            List<String> energyStorageCabinetSnlist = StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(templateId);
//            if (CollUtil.isNotEmpty(energyStorageCabinetSnlist)) {
//                LocalDateTime now = LocalDateTime.now();
//
//                for (String energyStorageCabinetSn : energyStorageCabinetSnlist) {
//                    // 线程：必须清除
//                    StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
//                    if (strategyThreadNew != null) {
//                        strategyThreadNew.remove();
//                    }
//                    StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.remove(energyStorageCabinetSn);
//
//                    // 型号：可以不清除
//                    // sn：可以不清除
//
//                    // 新增更新历史记录
//                    ChargeDischargeStrategyHistory chargeDischargeStrategyHistory = new ChargeDischargeStrategyHistory();
//                    // 根据储能柜真实sn查询到设备信息
//                    EmsDevice emsDevice = emsDeviceService.getOne(new QueryWrapper<EmsDevice>().eq("energy_storage_cabinet_sn", energyStorageCabinetSn));
//                    chargeDischargeStrategyHistory.setDeviceName(emsDevice.getName());
//                    chargeDischargeStrategyHistory.setDeviceSn(energyStorageCabinetSn);
//                    chargeDischargeStrategyHistory.setStationSn(emsDevice.getStationSn());
//                    chargeDischargeStrategyHistory.setCreateBy(createId.toString());
//                    chargeDischargeStrategyHistory.setCreateTime(now);
//                    chargeDischargeStrategyHistory.setSource(ChargeDischargeStrategyHistory.StrategySource.TRANSFORMER_WHOLE.getSourceType());
//                    chargeDischargeStrategyHistory.setDetail(historyString);
//                    chargeDischargeStrategyHistoryService.save(chargeDischargeStrategyHistory);
//                }
//            }
//            StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.remove(templateId);
//            templateIdList.remove(templateId);
//        }
//
//        // 清除变量
//        StrategyConstantNew.STRATEGY_TEMPLATE_ID.put(transformerSn, templateIdList);
//    }

    // 删除配置下的配置明细：删除策略模板 id 下 储能柜 的对应
//    public static void delTemplateIdEnergyStorageCabinetSn(Integer templateId, List<String> energyStorageCabinetSnList, Integer createId, String historyString) {
//        // 储能柜真实sn list
//        List<String> energyStorageCabinetSnlist = StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(templateId);
//        if (CollUtil.isNotEmpty(energyStorageCabinetSnlist)) {
//
//            LocalDateTime now = LocalDateTime.now();
//
//            for (String energyStorageCabinetSn : energyStorageCabinetSnList) {
//                // 循环清除
//                // 线程：必须清除
//                StrategyThreadNew strategyThreadNew = StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.get(energyStorageCabinetSn);
//                if (strategyThreadNew != null) {
//                    strategyThreadNew.remove();
//                }
//                StrategyConstantNew.STRATEGY_CABINET_SN_THREAD.remove(energyStorageCabinetSn);
//
//                // 型号：可以不清除
//                // sn：可以不清除
//                energyStorageCabinetSnlist.remove(energyStorageCabinetSn);
//                StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.put(templateId, energyStorageCabinetSnlist);
//
//                // 新增更新历史记录
//                ChargeDischargeStrategyHistory chargeDischargeStrategyHistory = new ChargeDischargeStrategyHistory();
//                // 根据储能柜真实sn查询到设备信息
//                EmsDevice emsDevice = emsDeviceService.getOne(new QueryWrapper<EmsDevice>().eq("energy_storage_cabinet_sn", energyStorageCabinetSn));
//                chargeDischargeStrategyHistory.setDeviceName(emsDevice.getName());
//                chargeDischargeStrategyHistory.setDeviceSn(energyStorageCabinetSn);
//                chargeDischargeStrategyHistory.setStationSn(emsDevice.getStationSn());
//                chargeDischargeStrategyHistory.setCreateBy(createId.toString());
//                chargeDischargeStrategyHistory.setCreateTime(now);
//                chargeDischargeStrategyHistory.setSource(ChargeDischargeStrategyHistory.StrategySource.TRANSFORMER_WHOLE.getSourceType());
//                chargeDischargeStrategyHistory.setDetail(historyString);
//                chargeDischargeStrategyHistoryService.save(chargeDischargeStrategyHistory);
//
//            }
//        }
//    }

    // 新增配置下的配置明细(增加储能柜)
//    public static void addStrategyConfigurationDetail(Integer templateId, List<String> newEnergyStorageCabinetSnList, Integer createId, String historyString) {
//        // 增加储能柜
//        List<String> energyStorageCabinetSnList = StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.get(templateId);
//        if (CollUtil.isEmpty(energyStorageCabinetSnList)) {
//            energyStorageCabinetSnList = new ArrayList<>();
//        }
//        energyStorageCabinetSnList.addAll(newEnergyStorageCabinetSnList);
//        StrategyConstantNew.STRATEGY_TEMPLATE_CABINET.put(templateId, energyStorageCabinetSnList);
//
//        // 线程不在这里处理
////        StrategyConstantNew.STRATEGY_CABINET_SN_THREAD
//
//        // 所有储能柜型号
//        List<EmsDeviceModel> energyStorageCabinetModelList = emsDeviceModelService.list();
//
//        LocalDateTime now = LocalDateTime.now();
//
//        for (String energyStorageCabinetSn : newEnergyStorageCabinetSnList) {
//            // 获得emsDevice
//            EmsDevice emsDevice = emsDeviceService.getOne(new QueryWrapper<EmsDevice>().eq("energy_storage_cabinet_sn", energyStorageCabinetSn));
//            EmsDeviceModel emsDeviceModel = energyStorageCabinetModelList.stream().filter(e -> Objects.equals(e.getId(), emsDevice.getEmsDeviceModelId())).findFirst().get();
//            // 储能柜型号
//            StrategyConstantNew.STRATEGY_CABINET_SN_EMSDEVICEMODEL.put(emsDevice.getEnergyStorageCabinetSn(), emsDeviceModel);
//            // 储能柜拓扑sn
//            StrategyConstantNew.STRATEGY_CABINET_SN_PRODUCT_DEVICE_SN.put(emsDevice.getEnergyStorageCabinetSn(), emsDevice.getProductDeviceSn());
//
//            // 新增更新历史记录
//            ChargeDischargeStrategyHistory chargeDischargeStrategyHistory = new ChargeDischargeStrategyHistory();
//            chargeDischargeStrategyHistory.setDeviceName(emsDevice.getName());
//            chargeDischargeStrategyHistory.setDeviceSn(energyStorageCabinetSn);
//            chargeDischargeStrategyHistory.setStationSn(emsDevice.getStationSn());
//            chargeDischargeStrategyHistory.setCreateBy(createId.toString());
//            chargeDischargeStrategyHistory.setCreateTime(now);
//            chargeDischargeStrategyHistory.setSource(ChargeDischargeStrategyHistory.StrategySource.TRANSFORMER_WHOLE.getSourceType());
//            chargeDischargeStrategyHistory.setDetail(historyString);
//            chargeDischargeStrategyHistoryService.save(chargeDischargeStrategyHistory);
//        }
//
//    }

    /**
     * 获得储能柜通道是否在线
     */
//    public boolean getIsOnline(String monitorChannelSn) {
//        boolean isOnline = false;
//
//        // 沟通状态
//        Double communicationStatus = ObjectToDouble(getResult("CD__" + (monitorChannelSn.contains("YXKX_YXKX") ? monitorChannelSn.substring(5) : monitorChannelSn) + "__COMMUNICATION_STATUS"));
//        if (Objects.equals(communicationStatus, 1D)) {
//            isOnline = true;
//        }
//
//        return isOnline;
//    }


}
