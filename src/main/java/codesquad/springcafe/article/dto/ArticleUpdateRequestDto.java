package codesquad.springcafe.article.dto;

import codesquad.springcafe.article.Article;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class ArticleUpdateRequestDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String contents;

    public ArticleUpdateRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Article toEntity(Long id, String writer) {
        return new Article(id, writer, title, contents, LocalDateTime.now());
    }
}
