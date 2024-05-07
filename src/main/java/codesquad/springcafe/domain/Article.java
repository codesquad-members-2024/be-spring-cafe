package codesquad.springcafe.domain;

import codesquad.springcafe.dto.ArticleDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private long articleId;
    private String writer;
    private String title;
    private String content;
    private long views;
    private boolean deleted;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public Article(long articleId, String writer, String title, String content, long views, boolean deleted,
                   LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = views;
        this.deleted = deleted;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Article(String userId, ArticleDto articleDto) {
        this.writer = userId;
        this.title = articleDto.getTitle();
        this.content = articleDto.getContent();
        this.views = 0;
        this.deleted = false;
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

    public long getArticleId() {
        return articleId;
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

    public boolean isDeleted() {
        return deleted;
    }

    public String getCreatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getLastModifiedDate() {
        return lastModifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
