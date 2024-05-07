package codesquad.springcafe.dto;

import codesquad.springcafe.model.Comment;
import jakarta.validation.constraints.NotBlank;

public class CommentWriteDto {
    private String userId;
    @NotBlank
    private String content;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    public Comment createComment(long articleId, String userId) {
        return new Comment(userId, articleId, content);
    }
}
