package codesquad.springcafe.comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment {

    private final Long commentId;
    private final String content;
    private final String writer;
    private final Long articleId;
    private final LocalDateTime createdTime;

    public Comment(Long commentId, String content, String writer, Long articleId, LocalDateTime createdTime) {
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.articleId = articleId;
        this.createdTime = createdTime;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public Long getArticleId() {
        return articleId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String getFormattedTime() {
        return this.createdTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
