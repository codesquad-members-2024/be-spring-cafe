package codesquad.springcafe.articles.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private long articleId;
    private String userId;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    private long pageViews;

    public Article(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.creationDate = LocalDateTime.now();
        this.pageViews = 0;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setPageViews(long pageViews) {
        this.pageViews = pageViews;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "userId : " + userId + ", title : " + title + ", content : " + content + ", creatinDate : " + creationDate;
    }

    public long getArticleId() {
        return articleId;
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

    public String getCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.creationDate.format(formatter);
    }

    public long getPageViews() {
        return pageViews;
    }
}
