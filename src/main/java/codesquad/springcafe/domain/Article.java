package codesquad.springcafe.domain;

import codesquad.springcafe.dto.ArticleDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private long id;
    private String writer;
    private String title;
    private String content;
    private long views;
    private LocalDateTime created;

    public Article(long id, String writer, String title, String content, long views, LocalDateTime created) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = views;
        this.created = created;
    }

    public Article(String userId, String title, String content) {
        this.writer = userId;
        this.title = title;
        this.content = content;
        this.views = 0;
        this.created = LocalDateTime.now();
    }

    public Article(String userId, ArticleDto articleDto) {
        this.writer = userId;
        this.title = articleDto.getTitle();
        this.content = articleDto.getContent();
        this.views = 0;
        this.created = LocalDateTime.now();
    }

    public void update(ArticleDto articleDto) {
        this.title = articleDto.getTitle();
        this.content = articleDto.getContent();
    }

    public long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreated() {
        return created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public long getViews() {
        return views;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
