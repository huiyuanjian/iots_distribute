package app.handler;

import app.entity.Message;
import app.service.RedisService;
import app.utils.config.ConfigInfo;
import app.utils.config.ConfigUtils;
import app.utils.spring.SpringContextUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 业务处理类
 */
@Slf4j
public class DistributerHandler {

    @Autowired
    private static KafkaTemplate kafkaTemplate;

    /**
     * 定义一个阻塞长度为10240长度的队列
     * 配置 主题 broker2
     */
    private static BlockingQueue<String> queue_data = new ArrayBlockingQueue<String>(10240);

    /**
     * 将数据放进阻塞队列中
     */
    public static boolean put(String data){
        try {
            queue_data.put(data);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 将数据转发到大数据
     */
    private static void distribute(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RedisService redisService = (RedisService) SpringContextUtils.getBean("iotsRegistInfoServerImpl");
            while (true){
                try {
                    // 这里获取到的数据 是Message 的json 字符串
                    String data = queue_data.take();
                    // 转化为Message
                    // 先将数据转发到大数据的kafka中
                    ListenableFuture topic = kafkaTemplate.send("topic", data);// TODO topic需要从配置文件中读取
                    // 再将数据更新到redis中,解析
                    Message message = JSONObject.parseObject(data, Message.class);
                    if(message != null && message.getBody() != null && message.getBody().getContext() != null){
                        // 解析
                        List<String> context = JSONObject.parseObject(message.getBody().getContext().get(0), List.class);
                        if (context != null && context.size() > 0){
                            List<Map<String, String>> lists = purificationData(parseUrl(context));
                            redisService.set(lists);
                        }
                    }

                } catch (InterruptedException e) {
                    log.error("处理配置信息出现异常！异常信息是：{}",e.getMessage());
                }
            }
            }
        },"数据转发线程").start();
    }

    // 解析每一个 转发到大数据的数据, 并保存到redis
    private static List<List<String>> parseUrl(List<String> context){
        List<List<String>> lists = new ArrayList<>();
        for(String str : context){
            int index = str.indexOf("//");
            // 从当前位置截取到最后的位置
            String newStr = str.substring(index + 2, str.length());
            List<String> strings = Arrays.asList(newStr.split("/"));
            String s = strings.get(strings.size() - 1);
            List<String> list = new ArrayList<>();
            for(int i = 0; i < strings.size(); i++){
                if (i != (strings.size() - 1)){
                    list.add(strings.get(i));
                }
            }
            list.addAll(Arrays.asList(s.split(",")));
            lists.add(list);
        }
        return lists;
    }

    /**
     * 分离出变量ID 和变量值
     * @param list
     * @return
     */
    private static List<Map<String, String>> purificationData(List<List<String>> list){
        List<Map<String, String>> varLists = new ArrayList<>();
        Map<String, String> map = null;
        for (List<String> varList : list){
            map = new HashMap<>();
            // 获取变量ID 和变量值
            String varId = varList.get(varList.size() - 3);
            String varValue = varList.get(varList.size() - 2);
            map.put(varId,varValue);
            varLists.add(map);
        }
        return varLists;
    }
}
