package com.yc.redis.string;

import redis.clients.jedis.Jedis;

/**
 * @Description 计数器
 * 应用场景：
 *      1、网站的访客数量
 *      2、用户执行某个操作的次数
 *      3、某首歌或某个视频的播放量
 *      4、帖子的回复数量
 * @auther wcc
 * @create 2019-11-15 21:45
 */
public class Counter {

    private Jedis jedis;

    private String key;

    public Counter(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

    /**
     * incr key
     * 加一操作
     * 如果 key 不存在，redis会先把key设为0，然后执行加一操作
     * @return
     */
    public Long increase(){
        return jedis.incr(key);
    }

    /**
     * decr key
     * 减一操作
     * 如果 key 不存在，redis会先把key设为0，然后执行减一操作
     * @return
     */
    public Long decrease(){
        return jedis.decr(key);
    }

    /**
     * get key
     * 返回计数器当前值
     * @return
     */
    public String get(){
        return jedis.get(key);
    }

    /**
     * getset key value
     * 清零计数器，并返回清零之前的值
     * @return
     */
    public int set(){
        String old_value = jedis.getSet(key, "0");
        return old_value == null ? 0 : Integer.valueOf(old_value);
    }

}
