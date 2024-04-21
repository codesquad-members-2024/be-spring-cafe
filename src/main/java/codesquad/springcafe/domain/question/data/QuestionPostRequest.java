package codesquad.springcafe.domain.question.data;

import codesquad.springcafe.domain.question.model.Question;
import jakarta.validation.constraints.NotBlank;

import java.beans.ConstructorProperties;

public class QuestionPostRequest {
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private final String title;
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private final String content;

    @ConstructorProperties({"title", "content"})
    public QuestionPostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Question toQuestion(Long userId) {
        return new Question(userId, this.title, this.content, 0);
    }
}
