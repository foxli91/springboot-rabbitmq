package com.is.springboot.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author li-shuai
 * @Description //todo
 * @ClassName SpringRabbitConfig
 * @date 2020.11.11 22:42
 */
@Component
public class SpringRabbitConfig {
    public static final String TOPIC_EXCHANGE_NAME = "sbt_topic_message_exchange";

    public static final String QUEUE_NAME = "sbt_topic_alibaba_queue";
    private static final String QUEUE_NAME_M = "sbt_topic_ms_queue";

    /**
     * 监听队列
     */
    @RabbitListener(queues = QUEUE_NAME)
    public void getMessage(Message message) {
        System.out.println("这是获取到的message" + new String(message.getBody()));

    }

    /**
     * 监听队列
     */
    @RabbitListener(queues = "sbt_topic_ms_queue")
    public void getMsMessage(Message message) {
        System.out.println("这是msQueue 获取到的message" + new String(message.getBody()));

    }


}
