package com.yc.redis.string;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * @Description ID 生成器
 * @auther wcc
 * @create 2019-11-15 21:23
 */
public class ID_Generator {

    private Jedis jedis;

    private String key;

    public ID_Generator(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

    /**
     * incr：对整数值执行加1操作
     * @return
     */
    public Long produce(){
        return jedis.incr(key);
    }

    /**
     * 保留前n个ID
     * @param n
     * @return
     */
    public String reserve(Long n){
        return jedis.set(key, ""+n, SetParams.setParams().nx());
    }

}
