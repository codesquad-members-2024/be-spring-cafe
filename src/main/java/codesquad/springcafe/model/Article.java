package codesquad.springcafe.model;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import codesquad.springcafe.dto.user.UserInfoDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Article {
    private final Long id;
    private final LocalDateTime timestamp;
    private final String writer;
    private final String title;
    private final String content;

    public Article(Long id, LocalDateTime timestamp, String writer, String title, String content) {
        this.id = id;
        this.timestamp = timestamp;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
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
        return new ArticleInfoDTO(id, timestamp, writer, title, content);
    }
}