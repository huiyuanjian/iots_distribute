package app.utils.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制接口配置信息实体类(用于持久化数据和服务间的通讯)
 */
@Data
@Slf4j
public class ConfigInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 服务id（数据库中的id）
    private String serverid;

    // 服务名称
    private String server_name;

    // 服务类型（字母全大写）
    private String serverType = "DISTRIBUTE";

    // 服务的mac地址（用于topic）
    private String macaddress = null;

    /**
     * 心跳间隔（单位是秒）
     * 默认心跳间隔是60秒
     */
    private int send_palpitate_interval = 60;

    /**
     * 服务说明
     */
    private String server_remark;

    /**
     * 分发接口需要转发kafka上哪些topic下的数据
     */
    public List<String> kafka_topic_distribute = new ArrayList<>();

    /**
     * mqtt 注册 主题 broker2
     */
    private String mqtt_regist_topic = null;

    /**
     * mqtt 配置 主题 broker2
     */
    private String mqtt_config_topic = null;

    /**
     * mqtt web应答 主题 broker2
     */
    private String mqtt_answer_web_topic = null;

    /**
     * 存放日志上传后的访问路径
     */
    private Map<String,Map<String,String>> map_logs = new HashMap<>();
}
