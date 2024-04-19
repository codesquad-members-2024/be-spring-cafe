package codesquad.springcafe.dto.article;

import codesquad.springcafe.model.Article;
import java.time.LocalDateTime;

public class UploadDTO {
    private final String title;
    private final String writer;
    private final String contents;

    public UploadDTO(String title, String writer, String contents) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
    }

    public Article toArticle(Long index) {
        return new Article(index, LocalDateTime.now(), title, writer, contents);
    }
}