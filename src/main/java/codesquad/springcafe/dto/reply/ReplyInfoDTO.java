package codesquad.springcafe.dto.reply;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReplyInfoDTO {
    private final Long articleId;
    private final Long index;
    private final String writer;
    private final LocalDateTime timestamp;
    private final String content;

    public ReplyInfoDTO(Long articleId, Long index, String writer, LocalDateTime timestamp, String content) {
        this.articleId = articleId;
        this.index = index;
        this.writer = writer;
        this.timestamp = timestamp;
        this.content = content;
    }

    public Long getArticleId() {
        return articleId;
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
}
