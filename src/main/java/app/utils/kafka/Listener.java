package app.utils.kafka;

import app.handler.DistributerHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class Listener {

    @KafkaListener(topics = "${kafka-config.consumer.topic}")
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("kafka的key: {}",record.key());
        log.info("kafka的value: {}",record.value().toString());
        // 交给数据队列，处理业务逻辑
        DistributerHandler.put(record.value().toString());
    }
}