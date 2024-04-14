package codesquad.springcafe.domain;

import java.time.LocalDateTime;

public class Article {
    private Long articleId;
    private String title;
    private String description;
    private String author;
    private LocalDateTime created;
    private int views;

    public Article(Long articleId, String title, String description, String author, LocalDateTime created, int views) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.author = author;
        this.created = created;
        this.views = views;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public int getViews() {
        return views;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
