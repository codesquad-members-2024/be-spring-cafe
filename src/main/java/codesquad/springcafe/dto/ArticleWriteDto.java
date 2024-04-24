package codesquad.springcafe.dto;

import codesquad.springcafe.model.Article;
import jakarta.validation.constraints.NotBlank;

public class ArticleWriteDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article createArticle(String userId) {
        return new Article(userId, title, content);
    }
}