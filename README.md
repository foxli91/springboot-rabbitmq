# springboot-rabbitmq
springboot整合rabbitmq的demo
最好使用docker来创建rabbitmq，这样会轻松一些
如果使用docker 有问题可以百度或者发邮件给我 lsdark_cl@foxmail.com

延迟队列插件方式
https://www.rabbitmq.com/community-plugins.html

![官方插件](E:\知识点截图\mq\官方插件.jpg)



rabbitmq_delayed_message_exchange-3.8.0.ez



#### 如果是安装在服务器的

下载的文件为zip格式，将zip格式解压，插件格式为ez，将文件复制到插件目录：

- Linux

```shell
/usr/lib/rabbitmq/lib/rabbitmq_server-xxxxx/plugins
```

##### 启动插件

- Linux

```shell
rabbitmq-plugins enable rabbitmq_delayed_message_exchange
```



#### 如果是安装在docker里面的

插件存放的位置

```shell
容器内：
[root@18e8052db138:/# ls -al
total 4
drwxr-xr-x   1 root root   28 Nov  9 15:07 .
drwxr-xr-x   1 root root   28 Nov  9 15:07 ..
-rwxr-xr-x   1 root root    0 Nov  9 15:07 .dockerenv
drwxr-xr-x   2 root root 4096 Sep 21 17:17 bin
drwxr-xr-x   2 root root    6 Apr 24  2018 boot
drwxr-xr-x   5 root root  340 Nov 14 02:37 dev
drwxr-xr-x   1 root root   22 Nov  9 15:07 etc
drwxr-xr-x   2 root root    6 Apr 24  2018 home
drwxr-xr-x   1 root root   30 Nov  6 01:50 lib
drwxr-xr-x   1 root root   34 Nov  6 01:40 lib64
drwxr-xr-x   2 root root    6 Sep 21 17:14 media
drwxr-xr-x   2 root root    6 Sep 21 17:14 mnt
drwxr-xr-x   1 root root   22 Nov  6 01:51 opt
lrwxrwxrwx   1 root root   21 Nov  6 01:51 plugins -> /opt/rabbitmq/plugins  # 这就是插件存放的目录
dr-xr-xr-x 111 root root    0 Nov 14 02:37 proc
drwx------   1 root root   24 Nov  6 01:50 root
drwxr-xr-x   1 root root   21 Sep 25 22:33 run
drwxr-xr-x   1 root root   21 Sep 25 22:33 sbin

```

将插件拷贝进容器

```shell
[root@lishuai springboot]# docker cp ./rabbitmq_delayed_message_exchange-3.8.0.ez 18e8052db138:/opt/rabbitmq/plugins

```

在容器内启动插件

```shell
root@18e8052db138:/opt/rabbitmq/plugins# rabbitmq-plugins enable rabbitmq_delayed_message_exchange
Enabling plugins on node rabbit@18e8052db138:
rabbitmq_delayed_message_exchange
The following plugins have been configured:
  rabbitmq_delayed_message_exchange
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_prometheus
  rabbitmq_web_dispatch
Applying plugin configuration to rabbit@18e8052db138...
The following plugins have been enabled:
  rabbitmq_delayed_message_exchange

started 1 plugins.

```



![延迟效果展示](E:\知识点截图\mq\延迟效果展示.jpg)
