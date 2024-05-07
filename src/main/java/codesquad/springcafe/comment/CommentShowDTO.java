package codesquad.springcafe.comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentShowDTO {
    private final Long commentId;
    private final String writer;
    private final String content;
    private final LocalDateTime lastEditTime;
    private boolean isEdited;
    private boolean isLoginUserWriter = false;

    public CommentShowDTO(Long commentId, String writer, String content, LocalDateTime lastEditTime, boolean isEdited) {
        this.commentId = commentId;
        this.writer = writer;
        this.content = content;
        this.lastEditTime = lastEditTime;
        this.isEdited = isEdited;
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

    public LocalDateTime getLastEditTime() {
        return lastEditTime;
    }

    public String getFormattedTime() {
        return this.lastEditTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public void setIsLoginUserWriter(boolean isLoginUserWriter) {
        this.isLoginUserWriter = isLoginUserWriter;
    }

    public boolean getIsLoginUserWriter() {
        return isLoginUserWriter;
    }
}
