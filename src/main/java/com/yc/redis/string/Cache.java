package com.yc.redis.string;

import redis.clients.jedis.Jedis;

/**
 * @Description 缓存
 * 使用：
 *  1、缓存HTML页面
 *  2、缓存热门图片
 * @auther wcc
 * @create 2019-11-15 10:26
 */
public class Cache {
    private Jedis jedis;

    public Cache(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 把需要存储的数据存储到key中，如果key存在，那么使用新值覆盖旧值
     * @param key
     * @param value
     */
    public void set(String key, String value){
        jedis.set(key, value);
    }

    /**
     * 获取存储在键key里面的数据，如果不存在返回null
     * @param key
     * @return
     */
    public String get(String key){
        return jedis.get(key);
    }

    /**
     * 对键key存储的缓存数据进行更新，并返回键key在被更新之前存储的数据
     * 如果键key不存在，返回null
     * @param key
     * @param new_value
     * @return
     */
    public String update(String key, String new_value){
        return jedis.getSet(key, new_value);
    }


}
