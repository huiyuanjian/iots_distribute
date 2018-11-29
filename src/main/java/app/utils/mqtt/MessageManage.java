package app.utils.mqtt;

import app.entity.Message;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 消息管理类
 */
public class MessageManage {

    /**
     * 控制消息管理容器
     */
    public static Map<String, Message> MESSAGE_MAP = new LinkedHashMap<>();

    /**
     * 控制消息的结果
     */
    private static Map<String, CtrlResult> RESULT_MAP = new LinkedHashMap <>();

    /**
     * 将执行结果放入管理器中
     */
    public static void put(String key){
        CtrlResult ctrlResult = new CtrlResult();
        ctrlResult.setKey(key);
        RESULT_MAP.put(key,ctrlResult);
    }

    /**
     * 获得控制命令的执行结果
     */
    public static CtrlResult get(String key){
        return RESULT_MAP.get(key);
    }

    /**
     * 变更执行结果
     */
    public static boolean change(String key, boolean result){
        try {
            CtrlResult ctrlResult = RESULT_MAP.get(key);
            ctrlResult.setAnswer(true);
            ctrlResult.setResult(result);
            RESULT_MAP.put(key,ctrlResult);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 移除
     */
    public static void remove(String key){
        // 删除执行结果
        RESULT_MAP.remove(key);
    }

}
