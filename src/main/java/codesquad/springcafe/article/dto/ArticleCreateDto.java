package codesquad.springcafe.article.dto;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.ArticleBuilder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleCreateDto {

    private String title;
    private String author;
    private String contents;
    private String userId;
    private Timestamp createdTime;
    private boolean deleted;

    public ArticleCreateDto(String title, String author, String contents, String userId) {
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.userId = userId;
        this.createdTime = Timestamp.valueOf(getCreatedTime());
        this.deleted = false;
    }

    public Article toEntity() {
        return new ArticleBuilder().author(this.author).title(this.title)
            .contents(this.contents).userId(this.userId).createdTime(this.createdTime)
            .deleted(this.deleted).build();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContents() {
        return contents;
    }

    public String getUserId() {
        return userId;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public String getCreatedTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
