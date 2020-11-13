package com.is.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author li-shuai
 * @Description //todo
 * @ClassName ProducerMain
 * @date 2020.11.10 13:21
 */
public class ProducerMain {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置参数 虚拟机，用户名，密码....
        // 家里
//        factory.setHost("192.168.7.130");
        // 公司
        factory.setHost("172.16.121.145");
        factory.setVirtualHost("/lsv");
        factory.setUsername("lishuai");
        factory.setPassword("lishuai");
        factory.setPort(5672);
        // 创建连接 connection
        Connection connection = factory.newConnection();
        // 创建 channel
        Channel channel = connection.createChannel();
        // 创建 queue

        /** public com.rabbitmq.client.AMQP.Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
                    queue  队列的名称
                    durable 是否持久化，当mq重启之后还在
                    exclusive 是否独占，只有一个消费者监听这个队列
                                当connection 关闭的时候删除这个队列
                    autoDelete  是否自动删除，没有消费者的时候删除
                    arguments   参数
    }*/
        // 如果没有这个队列会创建，有则不会创建
    channel.queueDeclare("lsTest",true,false,false,null);
        // 发送消息
        String messages="我是发送的啊";
        /**public void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException {
                exchange 交换机名称，简单模式下的交换机会默认使用 ""
                routingKey  路由名称
                props  配置信息
                body   消息体

         */
        channel.basicPublish("","lsTest",null,messages.getBytes());

        // 释放资源
//        channel.close();
//        connection.close();

    }
}
