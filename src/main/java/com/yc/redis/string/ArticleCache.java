package com.yc.redis.string;

import com.yc.redis.beans.Article;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @Description 存储文章信息：最好使用散列方式存储，这里作为string练习，用字符串键的方式存储
 * 使用到的命令：
 *      1、msetnx    在键不存在的条件下，一次为多个字符串键设置值
 *      2、mset      一次为多个字符串键设置值
 *      3、mget      一次获取多个字符串键的值
 * @auther wcc
 * @create 2019-11-15 13:13
 */
public class ArticleCache {

    private Jedis jedis;

    private Integer id;

    private String title_key;

    private String content_key;

    private String author_key;

    private String create_key;

    public ArticleCache(Jedis jedis, Integer article_id) {
        this.jedis = jedis;
        this.id = article_id;
        this.title_key = "article:"+id+":title";
        this.content_key = "article:"+id+":content";
        this.author_key = "article:"+id+":author";
        this.create_key = "article:"+id+":create";
    }

    public Long create(String title, String content, String author){
        return jedis.msetnx(title_key, title,
                content_key, content,
                author_key, author,
                create_key, ""+System.currentTimeMillis());
    }

    public Article get(){
        List<String> result = jedis.mget(title_key, content_key, author_key, create_key);
        return new Article(id, result.get(0), result.get(1), result.get(2), Long.valueOf(result.get(3)));
    }

    public String update(String title, String content, String author){
        HashMap<String, String> fields = new HashMap<>();
        if(title != null)
            fields.put(title_key, title);
        if (content != null)
            fields.put(content_key, content);
        if(author != null)
            fields.put(author_key, author);

        Set<Map.Entry<String, String>> entrySet = fields.entrySet();
        //System.out.println(entrySet);
        String[] keysvalues = new String[entrySet.size()*2];
        int i = 0;
        for (Map.Entry<String, String> entry : entrySet){
            keysvalues[i++] = entry.getKey();
            keysvalues[i++] = entry.getValue();
        }

        return jedis.mset(keysvalues);
    }


}
