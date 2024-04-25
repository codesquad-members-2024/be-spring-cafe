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
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public Article(long id, String writer, String title, String content, long views, LocalDateTime createdDate,
                   LocalDateTime lastModifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = views;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Article(String userId, ArticleDto articleDto) {
        this.writer = userId;
        this.title = articleDto.getTitle();
        this.content = articleDto.getContent();
        this.views = 0;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public Article update(ArticleDto articleDto) {
        this.title = articleDto.getTitle();
        this.content = articleDto.getContent();
        this.lastModifiedDate = LocalDateTime.now();
        return this;
    }

    public boolean isWriter(String userId) {
        return this.writer.equals(userId);
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

    public long getViews() {
        return views;
    }

    public String getCreatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getLastModifiedDate() {
        return lastModifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
