package com.example.springbootrabbitmq.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * RabbitMQ 定时消息队列 消费监听回调
 */
@Slf4j
@Component
public class RabbitConsumer {

    private static RabbitConsumer rabbitConsumer;
    @Resource
    private WebSocketServerEndpoint webSocketServerEndpoint; //引入WebSocket

    /**
     * 构造方法注入rabbitTemplate
     */
    @PostConstruct
    public void init() {
        rabbitConsumer = this;
        rabbitConsumer.webSocketServerEndpoint = webSocketServerEndpoint;
    }

    @RabbitListener(queues = RabbitMQConfig.msg_queue) //监听队列
    public void msgReceive(String content, Message message, Channel channel) throws IOException {
        log.info("----------------接收到消息--------------------" + content);
        //发送给WebSocket 由WebSocket推送给前端
        rabbitConsumer.webSocketServerEndpoint.sendMessageOnline(content);
        // 存入redis

        // 存入数据库--只有写入数据库才确认消息已接收

        // 确认消息已接收
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
