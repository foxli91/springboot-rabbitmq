package com.is.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author li-shuai
 * @Description //todo
 * @ClassName ConsumerMain
 * @date 2020.11.10 22:46
 */
public class ConsumerWorkQueueMain {
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
        channel.queueDeclare("work_queues", true, false, false, null);
        /** String basicConsume(String queue, boolean autoAck, Consumer callback)
         * queue 队列名称
         * autoAck 是否自动确认 消费者收到消息之后自动给生产者说一声收到了
         * callback 回调函数
         * */
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
            }
        };
        channel.basicConsume("work_queues", true, consumer);

    }
}
