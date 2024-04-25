package codesquad.springcafe.article;

import java.sql.Timestamp;

public class ArticleBuilder {

    private Long articleId;
    private String author;
    private String title;
    private String contents;
    private Timestamp createdTime;
    private boolean deleted;

    public ArticleBuilder articleId(Long articleId) {
        this.articleId = articleId;
        return this;
    }

    public ArticleBuilder author(String author) {
        this.author = author;
        return this;
    }

    public ArticleBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ArticleBuilder contents(String contents) {
        this.contents = contents;
        return this;
    }

    public ArticleBuilder createdTime(Timestamp createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public ArticleBuilder deleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public Article build() {
        return new Article(articleId, title, author, contents, createdTime, deleted);
    }

}
