package codesquad.springcafe.dto;

import codesquad.springcafe.model.UpdatedArticle;
import jakarta.validation.constraints.NotBlank;

public class ArticleUpdateDto {
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

    public UpdatedArticle createUpdatedArticle(String userId) {
        return new UpdatedArticle(userId, title, content);
    }
}