package codesquad.springcafe.articles.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Article {
    private long articleId;
    private String userId;
    private String title;
    private String content;
    private LocalDate creationDate;
    private long pageViews;

    public Article(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.creationDate = LocalDate.now();
        this.pageViews = 0;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setPageViews(long pageViews) {
        this.pageViews = pageViews;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public long getPageViews() {
        return pageViews;
    }
}
