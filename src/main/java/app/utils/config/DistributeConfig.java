/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.htby.bksw.iots_collect.utils.config 
 * @author: zhouxidong   
 * @date: 2018年5月27日 下午2:33:31 
 */
package app.utils.config;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @ClassName: DistributeConfig
 * @Description: 关于分发接口服务的配置
 * @author: zhouxidong
 * @date: 2018年5月27日 下午2:33:31  
 */
@Slf4j
public class DistributeConfig {
	
	// 防止新建对象
    private DistributeConfig(){};

	// 分发制接口服务的服务状态
	public static DistributeServerStatusEnum SERVERSTATUS = DistributeServerStatusEnum.START_UP;

	/**
	 * 分发接口需要转发kafka上哪些topic下的数据
	 */
	public static List<String> KAFKA_TOPIC_DISTRIBUTE = new ArrayList<>();

	/**
	 * 服务id
	 */
	public static String SERVERID = null;

	/**
	 * 心跳间隔（单位是秒）
	 * 默认心跳间隔是60秒
	 */
	public static int SEND_PALPITATE_INTERVAL = 60;

	/**
	 * 服务类型
	 */
	public static String SERVERTYPE = "DISTRIBUTE";

	/**
	 * 服务名称
	 */
	public static String SERVERNAME = null;

	/**
	 * 服务说明
	 */
	public static String SERVERREMARK = null;

	/**
	 * mac地址
	 */
	public static String MACADDRESS = null;

	/**
	 * mqtt 注册 主题 broker2
	 */
	public static String MQTT_REGIST_TOPIC = null;

	/**
	 * mqtt 配置 主题 broker2
	 */
	public static String MQTT_CONFIG_TOPIC = null;

	/**
	 * mqtt web应答 主题 broker2
	 */
	public static String MQTT_ANSWER_WEB_TOPIC = null;

	/**
	 * 存放上传后的日志访问路径
	 */
	public static Map<String, Map<String,String>> MAP_LOGS = new HashMap<>();

//	/**
//	 * 更新config
//	 */
//	public static void setConfig(ConfigInfo configInfo){
//
//		SERVERID = configInfo.getServerid();// 服务id
//		SEND_PALPITATE_INTERVAL = configInfo.getSend_palpitate_interval();// 心跳topic
//		SERVERTYPE = configInfo.getServerType();// 服务类型
//		MQTT_ANSWER_WEB_TOPIC = configInfo.getMqtt_answer_web_topic();// web应答topic
//		MQTT_CONFIG_TOPIC = configInfo.getMqtt_config_topic();// 配置topic
//
//		MQTT_REGIST_TOPIC = configInfo.getMqtt_regist_topic();// 注册topic
//	}

}
