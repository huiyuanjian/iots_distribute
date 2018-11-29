package app.listener;

import java.util.concurrent.CountDownLatch;

/**
 * 监听管理类
 * @author 周西栋
 * @date
 * @param
 * @return
 */
public class ListenManager {

    public static CountDownLatch COUNT = new CountDownLatch(4); //监听计数器

    public static CountDownLatch ENABLE_SUBSCRIB = new CountDownLatch(1); //订阅 broker2 计数器

}
