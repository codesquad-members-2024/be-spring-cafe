package codesquad.springcafe.dto.article;

import codesquad.springcafe.model.Article;
import java.time.LocalDateTime;

public class UploadDTO {
    private final String writer;
    private final String title;
    private final String content;

    public UploadDTO(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Article toArticle(Long index) {
        return new Article(index, LocalDateTime.now(), writer, title, content);
    }
}