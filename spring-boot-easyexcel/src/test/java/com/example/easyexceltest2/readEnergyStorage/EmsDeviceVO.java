package com.example.easyexceltest2.readEnergyStorage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 储能设备对象
 */
@Getter
@Setter
@EqualsAndHashCode
public class EmsDeviceVO {

    // 设备目录
    private String deviceCatalog;

    // 设备名称
    private String deviceName;

    // 设备类型
    private String deviceType;

    // 设备编码
    private String deviceCode;

    // 最大需量
    private String maxDemand;

    // SN号
    private String sn;

    // 型号
    private String model;

    // 部署时间
    private String deployTime;


}
