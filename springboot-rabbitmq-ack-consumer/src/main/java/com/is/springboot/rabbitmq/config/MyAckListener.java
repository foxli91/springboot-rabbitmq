package com.is.springboot.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author li-shuai
 * @Description //todo
 * @ClassName MyListener
 * @date 2020.11.12 21:35
 */
@Component
public class MyAckListener implements MessageListener {
    @Override
    public void onMessage(Message message) {

    }


}
