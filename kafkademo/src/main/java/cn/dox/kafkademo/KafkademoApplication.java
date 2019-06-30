package cn.dox.kafkademo;

import cn.dox.kafkademo.mq.KafkaSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author: weidx
 * @date: 2019/6/30
 */
@SpringBootApplication
public class KafkademoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(KafkademoApplication.class, args);
        KafkaSender sender = context.getBean(KafkaSender.class);
        for (int i = 0; i < 3; i++) {
            //调用消息发送类中的消息发送方法
            sender.send("topic1", "ggasadsa");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
