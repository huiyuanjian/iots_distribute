package app.service.impl;

import app.service.RedisService;
import app.utils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Value("${redis-two.host}")
    private String redisHost;

    @Value("${redis-two.port}")
    private int redisPort;

    @Value("${redis-two.password}")
    private String redisPwd;

    // ioserver redis 存的key
    private final String IOSERVER_VARIABLE_KEY = "ioserverVariable";

    private final RedisUtils redisUtils = new RedisUtils(redisHost, redisPort, redisPwd);

    /**
     * 保存数据到redis
     * @param lists
     */
    @Override
    public void set(List<Map<String, String>> lists) {
        // 这里先按遍历存入redis, 但是当数据量非常大的时候得优化
        try{
            for(Map<String, String> map : lists){
                Set<String> keys = map.keySet();
                for(String key : keys){
                    String value = map.get(key);
                    redisUtils.hset(IOSERVER_VARIABLE_KEY + key, key,value);
                    // 这里过期时间设置为
                    redisUtils.expire(IOSERVER_VARIABLE_KEY + key,600);
                }
            }
        }catch (Exception e){

        }
    }
}
