package codesquad.springcafe.model.article;

import java.time.LocalDate;

public class Article {
    private long articleId;
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

    public Article(long articleId, String userId, String title, String content, LocalDate creationDate) {
        this.articleId = articleId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "userId : " + userId + ", title : " + title + ", content : " + content + ", creatinDate : " + creationDate;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
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
}
