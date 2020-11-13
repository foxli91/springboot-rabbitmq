package com.is.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author li-shuai
 * @Description //todo
 * @ClassName SpringRabbitConfig
 * @date 2020.11.11 22:42
 */
@Configuration
public class SpringRabbitConfig {
    public static final String TOPIC_EXCHANGE_NAME="sbt_topic_message_exchange";

    public static final String QUEUE_NAME="sbt_topic_alibaba_queue";
    private static final String QUEUE_NAME_M="sbt_topic_ms_queue";
    // 交换机
    @Bean("bootExchange")
    public Exchange  createExchange(){
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NAME).durable(true).autoDelete().build();
    }
    // 队列
    @Bean("alibabaQueue")
    public Queue createQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    // 队列
    @Bean("msQueue")
    public Queue createMsQueue(){
        return QueueBuilder.durable(QUEUE_NAME_M).build();
    }
    // 绑定交换机和队列 binding
       // 1. 知道哪个队列
       // 2. 知道哪个交换机
       //  3. 设置routeKey
    @Bean
    public Binding bindingQueue(@Qualifier("alibabaQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

    @Bean
    public Binding bindingMsQueue(@Qualifier("msQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with("info.#").noargs();
    }

}
