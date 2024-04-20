package codesquad.springcafe.dto.article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleInfoDTO {
    private final Long index;
    private final LocalDateTime timestamp;
    private final String writer;
    private final String title;
    private final String content;

    public ArticleInfoDTO(Long index, LocalDateTime timestamp, String writer, String title, String content) {
        this.index = index;
        this.timestamp = timestamp;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Long getIndex() {
        return index;
    }

    public String getFormattedTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return timestamp.format(formatter);
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