package app.utils.mqtt;

import app.utils.config.DistributeConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 主题订阅
 * @author 周西栋
 * @date
 * @param
 * @return
 */
@Slf4j
public class TopicSubscrib {

    public void run(){
        MqttUtils mqttUtils = new MqttUtils();

        log.info(DistributeConfig.MQTT_CONFIG_TOPIC);
        log.info(DistributeConfig.MQTT_ANSWER_WEB_TOPIC);

        mqttUtils.subscribTopic(DistributeConfig.MQTT_CONFIG_TOPIC);
        mqttUtils.subscribTopic(DistributeConfig.MQTT_ANSWER_WEB_TOPIC);
    }
}
