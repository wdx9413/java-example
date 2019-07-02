package cn.dox.kafkademo.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: weidx
 * @date: 2019/6/30
 */

@Component
@Slf4j
public class KafkaConsumer {


    private final KafkaTemplate<Integer, String> kafkaTemplate;
    @Autowired
    public KafkaConsumer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @KafkaListener(topics = {"topic1"})
    public void processMessage(ConsumerRecord<Integer, String> record) {
        log.info("kafka process message start");
        log.info("process message, topic = [{}], data = [{}]", record.topic(), record.value());
        log.info("kafka process message end");
    }

}
