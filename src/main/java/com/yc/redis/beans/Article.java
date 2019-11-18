package com.yc.redis.beans;

/**
 * @Description 文章类
 * @auther wcc
 * @create 2019-11-15 13:59
 */
public class Article {

    private Integer id;

    private String title;

    private String content;

    private String author;

    private Long create;

    public Article(Integer id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Article(Integer id, String title, String content, String author, Long create) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.create = create;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getCreate() {
        return create;
    }

    public void setCreate(Long create) {
        this.create = create;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", create=" + create +
                '}';
    }
}
