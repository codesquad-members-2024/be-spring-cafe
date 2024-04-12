package model.article;

import db.ArticleDatabase;

import java.time.LocalDate;

public class Article {
    private String userId;
    private String title;
    private String content;
    private LocalDate creationDate;

    public Article(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.creationDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "userId : " + userId + ", title : " + title + ", content : " + content + ", creatinDate : " + creationDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
