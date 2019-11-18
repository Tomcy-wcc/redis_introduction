package com.yc.redis.hash;

import redis.clients.jedis.Jedis;

/**
 * @Description 短网址生成器
 * 应用场景：将长网址变成短网址，方便用户可以用来统计某个链接点击数
 * 原理：
 * 1、自增序列算法
 *      a、incr(ShortyUrl:id_counter)
 *      b、短网址 = base36(ShortyUrl:id_counter)
 *      c、hset(ShortyUrl:hash, 短网址， 长网址)
 * 2、md5截取变换法
 * @auther wcc
 * @create 2019-11-18 14:37
 */
public class ShortyUrl {
    private Jedis jedis;

    private static final String ID_COUNTER = "shorty_url:id_counter";

    private static final String URL_HASH = "shorty_url:url_hash";

    //字母表
    char[] base32 = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '0', '1', '2', '3', '4', '5'
    };

    public ShortyUrl(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 使用自增算法生成一个短网址
     * @param target_url
     * @return
     */
    public String shorten_incr(String target_url) {
        //产生一个ID
        Long new_id = jedis.incr(ID_COUNTER);
        //将ID进行转成36进制
        String short_id = Long.toString(new_id, 36);
        System.out.println(short_id);
        //将短网址和长网址设置到URL_HASH
        jedis.hset(URL_HASH, short_id, target_url);
        //将短网址返回
        return short_id;
    }

    /**
     * 使用md5截取变换法生成一个短网址
     * @param target_url
     * @return
     */
    public String shorten_md5(String target_url){
        return null;
    }

    /**
     * 根据短网址找到目标网址(长网址)
     * @param short_id
     * @return
     */
    public String restore(String short_id){
        return jedis.hget(URL_HASH, short_id);
    }


}
