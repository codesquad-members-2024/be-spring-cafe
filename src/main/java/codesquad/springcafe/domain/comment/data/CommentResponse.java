package codesquad.springcafe.domain.comment.data;

public class CommentResponse {
    private final Long commentId;
    private final String userLoginId;
    private final String userName;
    private final String content;
    private final String modifiedAt;
    private final boolean isModified;
    private final boolean isMy;
    private final boolean isUserWithdrawn;

    public CommentResponse(Long commentId, String userLoginId, String userName, String content, String modifiedAt, boolean isModified, boolean isMy, boolean isUserWithdrawn) {
        this.commentId = commentId;
        this.userLoginId = userLoginId;
        this.userName = userName;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.isModified = isModified;
        this.isMy = isMy;
        this.isUserWithdrawn = isUserWithdrawn;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getUserLoginId() {
        return userLoginId;
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

    public boolean isUserWithdrawn() {
        return isUserWithdrawn;
    }
}
