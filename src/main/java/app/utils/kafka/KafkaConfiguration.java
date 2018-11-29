package app.utils.kafka;

import app.utils.config.DistributeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 动态修改kafka订阅的topic
 */
@Configuration
@Slf4j
public class KafkaConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        // 从配置文件中动态获得需要在kafka上订阅的主题
        List<String> tops = DistributeConfig.KAFKA_TOPIC_DISTRIBUTE;
        log.info("kafka中的topic有：{}", Arrays.toString(tops.toArray()));
        if(!tops.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (String str: tops){
                sb.append(str).append(",");
            }
            String sb_str = sb.toString();
            String logicTopicName = sb_str.substring(0,sb_str.length()-2);
            System.setProperty("kafka-config.consumer.topic", logicTopicName);
            log.info("#########  system config topic:{} ########", logicTopicName);
        }
    }
}
