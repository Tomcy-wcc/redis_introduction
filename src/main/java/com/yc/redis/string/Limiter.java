package com.yc.redis.string;

import redis.clients.jedis.Jedis;

/**
 * @Description 限速器
 * 应用场景：
 *      1、防止账号被暴力破解
 *      2、防止爬虫（一个网页一分钟最多可以访问多少次）
 *
 * 原理：对每个IP设置最大访问次数-->每访问一次减一--->当最大访问次数为0时禁止访问
 * @auther wcc
 * @create 2019-11-18 13:46
 */
public class Limiter {

    private Jedis jedis;

    private String key;

    public Limiter(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

    /**
     * 设置最大访问次数
     * @param max_execute_times
     * @return
     */
    public String set_max_execute_times(String max_execute_times){
        return jedis.set(key, max_execute_times);
    }

    /**
     * 判断是否可以访问
     * @return
     */
    public boolean still_valid_to_execute(){
        Long num = jedis.decr(key);
        return num >= 0;
    }

    /**
     * 返回剩余执行次数
     * @return
     */
    public int remaining_execute_times(){
        Integer num = Integer.valueOf(jedis.get(key));
        return num < 0 ? 0 : num;
    }

}
