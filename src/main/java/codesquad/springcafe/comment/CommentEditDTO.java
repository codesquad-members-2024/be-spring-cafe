package codesquad.springcafe.comment;

import java.time.LocalDateTime;

public class CommentEditDTO {

    private final Long commentId;
    private final String content;
    private final String writer;
    private final LocalDateTime editedTime;

    public CommentEditDTO(Long commentId, String content, String writer) {
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.editedTime = LocalDateTime.now();
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

    public LocalDateTime getEditedTime() {
        return editedTime;
    }
}
