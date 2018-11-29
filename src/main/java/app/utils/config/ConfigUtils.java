package app.utils.config;

import app.entity.Message;
import app.entity.common.CommonConfig;
import app.utils.fileIO.FileUtils;
import app.utils.mqtt.MessageManage;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ConfigUtils {


    /**
     * 分发接口服务的服务状态变化关系
     */
    public DistributeServerStatusEnum change(boolean b){
        switch (DistributeConfig.SERVERSTATUS){
            case START_UP:
                DistributeConfig.SERVERSTATUS = DistributeServerStatusEnum.READ_LOCAL_CONFIGURATION;
                break;
            case READ_LOCAL_CONFIGURATION:
                if(b){
                    DistributeConfig.SERVERSTATUS = DistributeServerStatusEnum.SERVICE_START;
                }else {
                    DistributeConfig.SERVERSTATUS = DistributeServerStatusEnum.FAILED_READ_LOCAL_CONFIGURATION;
                }
                break;
            case FAILED_READ_LOCAL_CONFIGURATION:
                DistributeConfig.SERVERSTATUS = DistributeServerStatusEnum.WAITING_FOR_WEB_CONNECTION;
                break;
            case WAITING_FOR_WEB_CONNECTION:
                if(b){
                    DistributeConfig.SERVERSTATUS = DistributeServerStatusEnum.CONNECT_TO_WEB;
                }
                break;
            case CONNECT_TO_WEB:
                DistributeConfig.SERVERSTATUS = DistributeServerStatusEnum.WAITING_FOR_DISPATCH_CONFIGURATION;
                break;
            case WAITING_FOR_DISPATCH_CONFIGURATION:
                if(b){
                    DistributeConfig.SERVERSTATUS = DistributeServerStatusEnum.CONFIGURATION_SUCCESS;
                }
                break;
            case CONFIGURATION_SUCCESS:
                if (b){
                    DistributeConfig.SERVERSTATUS = DistributeServerStatusEnum.SERVICE_START;
                }else {
                    DistributeConfig.SERVERSTATUS = DistributeServerStatusEnum.SERVICE_FAILED;
                }
                break;
            default:
                break;
        }
        return DistributeConfig.SERVERSTATUS;
    }

    /**
     * 更新config
     */
    public void setConfig(ConfigInfo configInfo){

        DistributeConfig. SERVERID = configInfo.getServerid();// 服务id
        DistributeConfig.SEND_PALPITATE_INTERVAL = configInfo.getSend_palpitate_interval();// 心跳topic
        DistributeConfig.SERVERTYPE = configInfo.getServerType();// 服务类型
        DistributeConfig.MQTT_ANSWER_WEB_TOPIC = configInfo.getMqtt_answer_web_topic();// web应答topic
        DistributeConfig.MQTT_CONFIG_TOPIC = configInfo.getMqtt_config_topic();// 配置topic

        DistributeConfig.MQTT_REGIST_TOPIC = configInfo.getMqtt_regist_topic();// 注册topic
        DistributeConfig.KAFKA_TOPIC_DISTRIBUTE = configInfo.getKafka_topic_distribute();// kafka 主题
        DistributeConfig.SERVERNAME = configInfo.getServer_name();// 服务名称
        DistributeConfig.SERVERREMARK = configInfo.getServer_remark();// 服务说明

        // 持久化
        FileUtils fileUtils = new FileUtils();
        fileUtils.saveLoginInfo(getConfigFromDistribute());
    }

    /**
     * 更新config
     */
    public void setCommonConfig(CommonConfig commonConfig){

        FileUtils fileUtils = new FileUtils();
        // 配置消息
        ConfigInfo configInfo = fileUtils.readLoginInfo();
        if(configInfo != null){
            configInfo.setSend_palpitate_interval(commonConfig.getSend_palpitate_interval());// 心跳间隔
            configInfo.setServerid(commonConfig.getServer_id());// 服务id
            configInfo.setServerType(commonConfig.getServer_type());// 服务类型
            configInfo.setKafka_topic_distribute(commonConfig.getKafka_topic_distribute());// kafka topic
            configInfo.setServer_name(commonConfig.getServer_name());// 服务名称
            configInfo.setServer_remark(commonConfig.getServer_remark());// 服务说明

            // 更新
            setConfig(configInfo);
        } else {
            DistributeConfig. SERVERID = commonConfig.getServer_id();// 服务id
            DistributeConfig.SEND_PALPITATE_INTERVAL = commonConfig.getSend_palpitate_interval();// 心跳间隔
            DistributeConfig.SERVERTYPE = commonConfig.getServer_type();// 服务类型
            DistributeConfig.KAFKA_TOPIC_DISTRIBUTE = commonConfig.getKafka_topic_distribute();// kafka 主题
            DistributeConfig.SERVERREMARK = commonConfig.getServer_remark();
            DistributeConfig.SERVERNAME = commonConfig.getServer_name();

            // 持久化
            fileUtils.saveLoginInfo(getConfigFromDistribute());
        }
    }

    /**
     * 从内存中读取配置信息
     */
    public ConfigInfo getConfigFromDistribute(){
        ConfigInfo configInfo = new ConfigInfo();

        configInfo.setKafka_topic_distribute(DistributeConfig.KAFKA_TOPIC_DISTRIBUTE);
        configInfo.setServerType(DistributeConfig.SERVERTYPE);
        configInfo.setServerid(DistributeConfig.SERVERID);
        configInfo.setSend_palpitate_interval(DistributeConfig.SEND_PALPITATE_INTERVAL);
        configInfo.setMacaddress(DistributeConfig.MACADDRESS);

        configInfo.setMqtt_answer_web_topic(DistributeConfig.MQTT_ANSWER_WEB_TOPIC);
        configInfo.setMqtt_config_topic(DistributeConfig.MQTT_CONFIG_TOPIC);
        configInfo.setMqtt_regist_topic(DistributeConfig.MQTT_REGIST_TOPIC);
        configInfo.setServer_remark(DistributeConfig.SERVERREMARK);
        configInfo.setServer_name(DistributeConfig.SERVERNAME);

        configInfo.setMap_logs(DistributeConfig.MAP_LOGS);

        return configInfo;
    }
}
