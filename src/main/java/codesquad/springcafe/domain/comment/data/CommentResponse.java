package codesquad.springcafe.domain.comment.data;

public class CommentResponse {
    private final Long commentId;
    private final String userName;
    private final String content;
    private final String modifiedAt;
    private final boolean isModified;
    private final boolean isMy;

    public CommentResponse(Long commentId, String userName, String content, String modifiedAt, boolean isModified, boolean isMy) {
        this.commentId = commentId;
        this.userName = userName;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.isModified = isModified;
        this.isMy = isMy;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public boolean isModified() {
        return isModified;
    }

    public boolean isMy() {
        return isMy;
    }
}
