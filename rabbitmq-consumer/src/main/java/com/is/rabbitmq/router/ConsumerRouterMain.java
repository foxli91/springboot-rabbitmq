package com.is.rabbitmq.router;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author li-shuai
 * @Description //todo
 * @ClassName ConsumerMain
 * @date 2020.11.10 22:46
 */
public class ConsumerRouterMain {
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
        String microsoft="microsoft_direct_log";
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 这个是一个回调方法，当收到消息之后会自动执行该方法

            /****
             * consumerTag 标识 编号
             * envelope 获取一些信息例如交换机 key，路由
             * properties 配置信息
             * body 数据
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
//                System.out.println("标识:" + consumerTag);
//                System.out.println("一些信息" + envelope.getExchange());
//                System.out.println("这个是路由，其实是队列名称" + envelope.getRoutingKey());
//                System.out.println("消息序号" + envelope.getDeliveryTag());
//                System.out.println("配置信息:" + properties);
                System.out.println("消息" + new String(body));
                System.out.println("微软收到了哪些消息.....");
            }
        };
        channel.basicConsume(microsoft, true, consumer);

    }
}
