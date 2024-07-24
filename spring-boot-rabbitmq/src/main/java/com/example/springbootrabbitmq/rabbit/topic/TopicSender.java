package com.example.springbootrabbitmq.rabbit.topic;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootrabbitmq.config.Msg;
import com.example.springbootrabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TopicSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 只有topic.#能接收到，即只有Topic Receiver2能接收到
     */
    public void send() {
        String context = "hi, i am message all";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.1", context);
    }

    /**
     * topic.#和topic.message 能接收到，即Topic Receiver1和Topic Receiver2都能接收
     * TODO:重点：topic方式符合多个接收端条件，则-->就是说可以实现多个地点重复接收一个消息！！！
     */
    public void send1() {
        String context = "hi, i am message 1";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.message", context);
    }

    /**
     * 只有topic.#能接收到，即只有Topic Receiver2能接收到
     */
    public void send2() {
        String context = "hi, i am messages 2";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.messages", context);
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
