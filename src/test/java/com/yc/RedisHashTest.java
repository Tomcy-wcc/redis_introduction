package com.yc;

import com.yc.redis.hash.ShortyUrl;
import com.yc.redis.util.JedisUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @Description Redis 散列表的应用 测试
 * @auther wcc
 * @create 2019-11-18 16:55
 */
public class RedisHashTest {

    private Jedis jedis;


    @Before
    public void before(){
        jedis = JedisUtil.getJedis();
    }

    @Test
    public void testShorty_Url(){
        String target_url = "http://www.blog.tomcy.com/toLogin";
        ShortyUrl shortyUrl = new ShortyUrl(jedis);
        String shorten = shortyUrl.shorten_incr(target_url);
        System.out.println(shorten);

    }

    @After
    public void after(){
        JedisUtil.close(jedis);
    }
}
