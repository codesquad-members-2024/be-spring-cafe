package codesquad.springcafe.comment;

import java.time.LocalDateTime;

public class CommentShowDTO {
    private final Long commentId;
    private final String writer;
    private final String content;
    private final LocalDateTime createdTime;

    public CommentShowDTO(Long commentId, String writer, String content, LocalDateTime createdTime) {
        this.commentId = commentId;
        this.writer = writer;
        this.content = content;
        this.createdTime = createdTime;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}
