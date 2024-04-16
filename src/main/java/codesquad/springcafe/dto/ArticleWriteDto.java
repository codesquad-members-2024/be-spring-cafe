package codesquad.springcafe.dto;

import codesquad.springcafe.model.Article;
import jakarta.validation.constraints.NotBlank;

public class ArticleWriteDto {
    @NotBlank(message = "제목을 입력하세요")
    private final String title;
    @NotBlank(message = "내용을 입력하세요")
    private final String content;

    public ArticleWriteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article createArticle(String userId) {
        return new Article(userId, title, content);
    }
}