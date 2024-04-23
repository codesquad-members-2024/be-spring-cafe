package codesquad.springcafe.domain.comment.data;

import codesquad.springcafe.domain.comment.model.Comment;
import jakarta.validation.constraints.NotBlank;

public class CommentRequest {
    @NotBlank(message = "댓글은 필수 입력 값입니다.")
    private final String content;

    public CommentRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Comment toComment(Long userId, Long questionId) {
        return new Comment(userId, questionId, this.content);
    }
}
