package codesquad.springcafe.article.dto;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.ArticleBuilder;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ArticleCreateDto {

    private String title;
    private String author;
    private String contents;
    private Timestamp createdTime;
    private boolean deleted;

    public ArticleCreateDto(String title, String author, String contents) {
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.createdTime = Timestamp.valueOf(LocalDateTime.now());
        this.deleted = false;
    }

    public Article toEntity() {
        return new ArticleBuilder().author(this.author).title(this.title)
            .contents(this.contents).createdTime(this.createdTime)
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

    public boolean getDeleted() {
        return deleted;
    }
}
