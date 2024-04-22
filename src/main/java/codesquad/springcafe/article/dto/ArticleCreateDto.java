package codesquad.springcafe.article.dto;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.ArticleBuilder;

public class ArticleCreateDto {

    private String title;
    private String author;
    private String contents;
    private String userId;
    private boolean deleted;

    public ArticleCreateDto(String title, String author, String contents, String userId) {
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.userId = userId;
        this.deleted = false;
    }

    public Article toEntity() {
        return new ArticleBuilder().author(author).title(title)
            .contents(contents).userId(userId).deleted(deleted).build();
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
}
