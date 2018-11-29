package app.listener;

import app.utils.config.DistributeConfig;
import app.utils.sys.SysUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(value = 1)
public class ReadSystemPropertiesListener  implements ApplicationRunner {

    @Override
    public void run( ApplicationArguments applicationArguments ) throws Exception {
        log.info("第一步：读取系统信息");

        // 获取mac地址
        DistributeConfig.MACADDRESS = SysUtils.getMACAddress();

        // 初始化mqtt broker2 需要订阅的主题
        DistributeConfig.MQTT_REGIST_TOPIC = "REGIST/DISTRIBUTE/" + DistributeConfig.MACADDRESS; // 注册
        DistributeConfig.MQTT_CONFIG_TOPIC = "WEB/DISTRIBUTE/" + DistributeConfig.MACADDRESS + "/CONFIG"; // 配置
        DistributeConfig.MQTT_ANSWER_WEB_TOPIC = "WEB/DISTRIBUTE/" + DistributeConfig.MACADDRESS + "/ANSWER";// 应答

        log.info("我所用的mac地址是：{}",DistributeConfig.MACADDRESS);
        log.info("第一步：完成");
        // 执行完方法后，计数器减一
        ListenManager.COUNT.countDown();
    }
}
