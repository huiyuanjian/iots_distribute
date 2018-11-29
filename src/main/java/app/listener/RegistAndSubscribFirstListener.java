package app.listener;

import app.entity.Message;
import app.entity.common.CommonConfig;
import app.utils.config.DistributeConfig;
import app.utils.mqtt.MqttUtils;
import app.utils.templates.MessageTemplates;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(value = 2)
public class RegistAndSubscribFirstListener implements ApplicationRunner {

    @Override
    public void run( ApplicationArguments applicationArguments ) throws Exception {
        log.info("第二步：向 broker2 注册自身信息，并创建需要订阅的主题");

        MessageTemplates messageTemplates = new MessageTemplates();
        MqttMessage mqttMessage = new MqttMessage();

        // 发布
        MqttUtils mqttUtils = new MqttUtils();

        // 注册消息
        mqttMessage = messageTemplates.getMqttMessage(messageTemplates.getMessage(1,messageTemplates.getMesBody(0,messageTemplates.getRegistInfo())));
        mqttUtils.publish(DistributeConfig.MQTT_REGIST_TOPIC,mqttMessage);

        // 配置
        mqttMessage = messageTemplates.getMqttMessage(messageTemplates.getMessage(4,messageTemplates.getMesBody(0,new CommonConfig())));
        mqttUtils.publish(DistributeConfig.MQTT_CONFIG_TOPIC,mqttMessage);

        // 应答
        mqttMessage = messageTemplates.getMqttMessage(messageTemplates.getMessage(6,null));
        mqttUtils.publish(DistributeConfig.MQTT_ANSWER_WEB_TOPIC,mqttMessage);

        // 向下减一计数
        ListenManager.ENABLE_SUBSCRIB.countDown();

        log.info("第二步：完成");
        // 执行完方法后，计数器减一
        ListenManager.COUNT.countDown();
    }
}
