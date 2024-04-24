package codesquad.springcafe.dto.article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleInfoDTO {
    private final Long id;
    private final LocalDateTime timestamp;
    private final String writer;
    private final String title;
    private final String content;

    public ArticleInfoDTO(Long id, LocalDateTime timestamp, String writer, String title, String content) {
        this.id = id;
        this.timestamp = timestamp;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return timestamp.format(formatter);
    }


    public boolean isWriter(String userId) {
        return writer.equals(userId);
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
}