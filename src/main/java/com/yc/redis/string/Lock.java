package com.yc.redis.string;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * @Description 锁
 * 原理：根据给定的字符串键是否有值来判断锁是否已经被获取
 * @auther wcc
 * @create 2019-11-15 12:42
 */
public class Lock {

    private Jedis jedis;

    private static final String VALUE_OF_LOCK = "locking";

    private String key;

    public Lock(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

    /**
     * 加锁：
     * 原理：根据set key value [NX|XX]
     * NX：确保代表锁的字符串键值只会在没有值的情况下设置
     *      如果给定的key没有值--->没有锁定
     *      如果给定的key有值--->锁定
     * @return
     */
    public boolean acquire(){
        return jedis.set(key, VALUE_OF_LOCK, SetParams.setParams().nx()) != null;
    }

    /**
     * 释放锁
     * 删除key
     * @return
     */
    public boolean release(){
        return jedis.del(key) == 1;
    }

}
