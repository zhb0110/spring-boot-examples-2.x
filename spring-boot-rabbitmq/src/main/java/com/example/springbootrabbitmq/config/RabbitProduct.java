package com.example.springbootrabbitmq.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * RabbitMQ消息提供者
 */
@Slf4j
@Component
public class RabbitProduct {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public RabbitProduct(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    //发送消息 推送到websocket    参数自定义 转为String发送消息
    public void sendMSG(Msg msg) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息对象放入路由对应的队列当中去
        rabbitTemplate.convertAndSend(RabbitMQConfig.msg_exchang, RabbitMQConfig.msg_routing_key, JSONObject.toJSON(msg).toString(), correlationId);
    }

    // 发送设备消息
    public void sendDeviceMSG(JSONObject msg) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息对象放入路由对应的队列当中去
        rabbitTemplate.convertAndSend(RabbitMQConfig.msg_exchang, RabbitMQConfig.msg_routing_key, msg.toString(), correlationId);
    }
}
