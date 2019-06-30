package cn.dox.kafkademo.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author: weidx
 * @date: 2019/6/30
 */
@Component
@Slf4j
public class KafkaSender {

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String data) {
        log.info("kafka send message start");
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("kafka send message error, ex = [{}], topic = [{}], data = [{}]", throwable, topic, data);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> integerStringSendResult) {
                log.info("kafka send message success, topic = [{}], data = [{}]", topic, data);
            }
        });
    }

}
