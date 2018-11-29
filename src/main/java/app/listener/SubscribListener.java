package app.listener;

import app.utils.config.ConfigUtils;
import app.utils.config.DistributeConfig;
import app.utils.kafka.KafkaConsumerConfig;
import app.utils.kafka.Listener;
import app.utils.mqtt.TopicSubscrib;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(value = 4)
public class SubscribListener implements ApplicationRunner {

    @Override
    public void run( ApplicationArguments applicationArguments ) throws Exception {
        log.info("启用订阅");
        Thread.sleep(5000);
        TopicSubscrib topicSubscrib = new TopicSubscrib();
        topicSubscrib.run();

        log.info("测试用：topic");
        DistributeConfig.KAFKA_TOPIC_DISTRIBUTE.add("test");
        DistributeConfig.KAFKA_TOPIC_DISTRIBUTE.add("test1");
        DistributeConfig.KAFKA_TOPIC_DISTRIBUTE.add("test2");

        KafkaConsumerConfig consumerConfig = new KafkaConsumerConfig();
        Listener listener = consumerConfig.listener();

        // 执行完方法后，计数器减一
        ListenManager.COUNT.countDown();
    }
}
