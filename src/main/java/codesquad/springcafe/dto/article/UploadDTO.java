package codesquad.springcafe.dto.article;

import codesquad.springcafe.model.Article;
import java.time.LocalDateTime;

public class UploadDTO {
    private final String title;
    private final String writer;
    private final String content;

    public UploadDTO(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public Article toArticle(Long index) {
        return new Article(index, LocalDateTime.now(), title, writer, content);
    }
}