package com.yc.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description Jedis工具类
 * @auther wcc
 * @create 2019-09-23 10:15
 */
public class JedisUtil {

    private static JedisPool jedisPool;

    static {
        //连接池配置对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(10);
        //最大空闲数
        jedisPoolConfig.setMaxIdle(2);
        //最大等待时间
        jedisPoolConfig.setMaxWaitMillis(10*1000);
        //是否提前进行validate操作, 如果为true，则得到的jedis实例均是可用的
        jedisPoolConfig.setTestOnBorrow(true);
        //初始化连接池
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.72.135", 6379);
    }

    private static final JedisUtil jedisUtil = new JedisUtil();

    /**
     * 构造私有化
     */
    private JedisUtil(){

    }

    /**
     * 获取Jedis连接池
     * @return
     */
    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 获取Jedis
     * @return
     */
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     * 归还Jedis
     * @param jedis
     */
    public static void close(Jedis jedis){
        if (null != jedis && null != jedisPool){
            jedis.close();
        }
    }

}
