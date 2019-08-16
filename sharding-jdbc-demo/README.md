# sharding semphere demo

## sharding jdbc

### sharding

> 自行创建库（仅分库，分表自行查找官方文档）

### read write split

```docker
docker pull mysql:5.7

# 自行更换映射地址
docker run -d -e MYSQL_ROOT_PASSWORD=123456 --name mysql-master  -v D:\documents\project\example\sharding-jdbc-demo\db\mysql-master:/var/lib/mysql -v D:\documents\project\example\sharding-jdbc-demo\db\/my-master.cnf:/etc/mysql/my.cnf -p 3306:3306 mysql:5.7

docker run -d -e MYSQL_ROOT_PASSWORD=123456 --name mysql-slave1  -v D:\documents\project\example\sharding-jdbc-demo\db\mysql-slave1:/var/lib/mysql -v D:\documents\project\example\sharding-jdbc-demo\db\my-slave1.cnf:/etc/mysql/my.cnf -p 3307:3306 mysql:5.7

cat /etc/hosts

master:
	docker exec -it mysql-master bash 
	grant replication slave on *.* to 'slave'@'%' identified by 'slave';
	flush privileges;
	show master status;
slave:
	docker exec -it mysql-slave1 bash 
	change master to master_host='172.17.0.2',master_user='slave',master_password='slave',
master_log_file='mysql-bin.000004',master_log_pos=1160,master_port=3306;
	start slave;
	show slave status\G
```

@see [mysql-docker](https://github.com/wdx9413/mysql-docker)
