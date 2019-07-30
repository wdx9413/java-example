package cn.dox.jdbcdemo;

import org.apache.shardingsphere.core.config.DataSourceConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: weidx
 * @date: 2019/7/28
 */
@SpringBootApplication
public class ShardingJdbcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcDemoApplication.class, args);
    }

}
