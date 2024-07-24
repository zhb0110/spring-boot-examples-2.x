package com.example.springbootweb.demos.thread.constant;

//import com.eraworks.device.api.entity.ChargeDischargeStrategyTemplateDetail;
//import com.eraworks.device.api.entity.EmsDeviceModel;
//import com.eraworks.device.strategy2.thread.StrategyThreadNew;

import com.example.springbootweb.demos.thread.thread.StrategyThreadNew;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StrategyConstantNew {

    /**
     * Map<变压器sn，变压器通道sn>，变压器通道sn和储能柜的sn 基本不一样，除了宜兴等初始站点，用处不大，后期可能删
     */
    public final static ConcurrentHashMap<String, String> STRATEGY_MONITORCHANNELSN = new ConcurrentHashMap<>();
    /**
     * Map<变压器sn，动力柜sn>，变压器下的动力柜sn
     */
    public final static ConcurrentHashMap<String, List<String>> STRATEGY_POWER_CABINET_SN = new ConcurrentHashMap<>();


    /**
     * Map<变压器sn，最大需量>
     */
    public final static ConcurrentHashMap<String, Integer> STRATEGY_MAXDEMAND = new ConcurrentHashMap<>();

    /**
     * Map<策略模板id，策略模板明细>
     */
//    public static ConcurrentHashMap<Integer, List<ChargeDischargeStrategyTemplateDetail>> STRATEGY_TEMPLATE_DETAIL = new ConcurrentHashMap<>();

    /**
     * Map<变压器sn，策略模板id>
     */
    public static ConcurrentHashMap<String, List<Integer>> STRATEGY_TEMPLATE_ID = new ConcurrentHashMap<>();


    /**
     * Map<策略模板id，储能柜真实sn List>，如果更新策略配置和策略明细，则全改动
     */
    public final static ConcurrentHashMap<Integer, List<String>> STRATEGY_TEMPLATE_CABINET = new ConcurrentHashMap<>();


    /**
     * Map<储能柜真实sn，储能柜线程>，一一对应，入更新策略配置和策略明细，则全改动
     */
    public final static ConcurrentHashMap<String, StrategyThreadNew> STRATEGY_CABINET_SN_THREAD = new ConcurrentHashMap<>();



    /**
     * Map<储能柜真实sn，储能柜型号>，一一对应，对应功率，容量
     */
//    public final static ConcurrentHashMap<String, EmsDeviceModel> STRATEGY_CABINET_SN_EMSDEVICEMODEL = new ConcurrentHashMap<>();

    /**
     * Map<储能柜真实sn，储能柜sn(拓扑图sn)>，一一对应
     */
    public final static ConcurrentHashMap<String, String> STRATEGY_CABINET_SN_PRODUCT_DEVICE_SN = new ConcurrentHashMap<>();


}
