package codesquad.springcafe.dto.article;

import codesquad.springcafe.model.Article;
import java.time.LocalDateTime;

public class ArticleUploadDTO {
    private final String title;
    private final String writer;
    private final String content;

    public ArticleUploadDTO(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public Article toArticle(Long id) {
        return new Article(id, LocalDateTime.now(), title, writer, content);
    }
}