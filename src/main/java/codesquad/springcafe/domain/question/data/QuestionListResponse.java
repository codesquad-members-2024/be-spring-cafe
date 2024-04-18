package codesquad.springcafe.domain.question.data;

import java.util.List;

public class QuestionListResponse {
    private final Integer totalQuestionCnt;
    private final List<QuestionResponse> questions;

    public QuestionListResponse(List<QuestionResponse> questions) {
        this.totalQuestionCnt = questions.size();
        this.questions = questions;
    }

    public Integer getTotalQuestionCnt() {
        return totalQuestionCnt;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }
}
