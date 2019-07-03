# kafka 学习案例

1. spring boot 版本为2.1.6.RELEASE

2. 安装docker（docker-windows-desktop需要在某些命令前加winpty，具体含义可自行搜索）

3. 下载对应版本镜像 ： docker pull wurstmeister/kafka:2.12-2.0.1

4. 安装zookeeper（可以用wurstmeister/zookeeper镜像；楼主用的是个人阿里云主机zookeeper；具体操作不在扯了）

5. 启动容器 ： docker run  -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper_host:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://local_ip_by_ifconfig:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka:2.12-2.0.1

6. kafka基本配置：
    * 进入容器： docker exec -it kafka bash
    * 到/opt/kafka目录下创建topic： bin/kafka-topics.sh --create --zookeeper zookeeper_host:2181 --replication-factor 1 --partitions 1 --topic topic1
7. 编写springboot项目
