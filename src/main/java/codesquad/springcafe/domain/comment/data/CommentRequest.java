package codesquad.springcafe.domain.comment.data;

import codesquad.springcafe.domain.comment.model.Comment;
import codesquad.springcafe.domain.question.model.Question;
import codesquad.springcafe.domain.user.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.beans.ConstructorProperties;

public class CommentRequest {
    @NotNull(message = "질문 게시글 아이디는 필수 입력 값입니다.")
    private final Long questionId;
    @NotBlank(message = "댓글은 필수 입력 값입니다.")
    private final String content;

    @ConstructorProperties({"questionId", "content"})
    public CommentRequest(Long questionId, String content) {
        this.questionId = questionId;
        this.content = content;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getContent() {
        return content;
    }

    public Comment toComment(User user, Question question) {
        return new Comment(user, question.getId(), this.content);
    }
}
