package app.service;

import java.util.List;
import java.util.Map;

public interface RedisService {
    // 保存数据到redis
    void set(List<Map<String, String>> lists);
}
