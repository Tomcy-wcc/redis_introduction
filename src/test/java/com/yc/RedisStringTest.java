package com.yc;

import com.yc.redis.string.*;
import com.yc.redis.util.JedisUtil;
import com.yc.redis.beans.Article;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @Description Redis 字符串的应用 测试类
 * @auther wcc
 * @create 2019-11-15 12:36
 */
public class RedisStringTest {

    private Jedis jedis;


    @Before
    public void before(){
        jedis = JedisUtil.getJedis();
    }

    @Test
    public void testCache(){
        Cache cache = new Cache(jedis);

        cache.set("number", "10086");
        String number = cache.get("number");
        System.out.println(number);

        String old_value = cache.update("number", "10087");
        System.out.println(old_value);
        String new_value = cache.get("number");
        System.out.println(new_value);
    }

    @Test
    public void testLock(){
        Lock lock = new Lock(jedis, "lock");

        System.out.println(lock.acquire());
        System.out.println(lock.acquire());
        System.out.println(lock.acquire());

        lock.release();

        System.out.println(lock.acquire());
        System.out.println(lock.acquire());

        lock.release();
    }

    @Test
    public void testArticleCache(){
        Article article = new Article(1, "Git基础", "该命令将创建一个名为 .git 的子目录", "wcc");
        ArticleCache articleCache = new ArticleCache(jedis, article.getId());
        articleCache.create(article.getTitle(), article.getContent(), article.getAuthor());
        Article articleDB = articleCache.get();
        System.out.println(articleDB);
        articleCache.update("git分支", null, null);
        articleDB = articleCache.get();
        System.out.println(articleDB);
    }

    @Test
    public void testIDGenerator(){

        String s = jedis.brpoplpush("k2", "k1", 5);
        System.out.println(s);
        s = jedis.brpoplpush("k2", "k1", 5);
        System.out.println(s);
        s = jedis.brpoplpush("k2", "k1", 0);
        System.out.println(s);


        /*ID_Generator id_generator = new ID_Generator(jedis, "user:id");
        id_generator.reserve(100L);
        System.out.println(id_generator.produce());
        System.out.println(id_generator.produce());
        System.out.println(id_generator.produce());*/
    }

    @Test
    public void testCounter(){
        String key = "counter:blog:1";
        Counter counter = new Counter(jedis, key);
        System.out.println(counter.increase());
        System.out.println(counter.decrease());
        System.out.println(counter.set());
        System.out.println(counter.get());
    }

    @Test
    public void testLimiter(){
        String key = "limiter:192.168.72.135";
        Limiter limiter = new Limiter(jedis, key);
        limiter.set_max_execute_times(""+10);
        System.out.println(limiter.still_valid_to_execute());
        System.out.println(limiter.still_valid_to_execute());
        System.out.println(limiter.remaining_execute_times());
    }



    @After
    public void after(){
        JedisUtil.close(jedis);
    }
}
