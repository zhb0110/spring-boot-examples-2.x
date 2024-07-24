package com.example.springbootrabbitmq.config;

import lombok.Data;

@Data
public class Msg {

    // 设备名
    String deviceName = "";

    // 属性
    String attributes = "";

    // 实时值
    String telemetry = "";


    public Msg(String deviceName, String attributes, String telemetry) {
        this.deviceName = deviceName;
        this.attributes = attributes;
        this.telemetry = telemetry;
    }
}
