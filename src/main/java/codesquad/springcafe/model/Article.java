package codesquad.springcafe.model;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import java.time.LocalDateTime;

public class Article {
    private final Long index;
    private final LocalDateTime timestamp;
    private final String writer;
    private final String title;
    private final String content;

    public Article(Long index, LocalDateTime timestamp, String writer, String title, String content) {
        this.index = index;
        this.timestamp = timestamp;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Long getIndex() {
        return index;
    }

    public LocalDateTime getTimeStamp() {
        return timestamp;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ArticleInfoDTO toDTO() {
        return new ArticleInfoDTO(index, timestamp, writer, title, content);
    }
}