package codesquad.springcafe.dto;

import codesquad.springcafe.model.Comment;
import jakarta.validation.constraints.NotBlank;

public class CommentWriteDto {
    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment createComment(long articleId, String userId) {
        return new Comment(userId, articleId, content);
    }
}
