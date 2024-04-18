package codesquad.springcafe.domain.question.data;

public class QuestionResponse {
    private final Long questionId;
    private final String userName;
    private final String userLoginId;
    private final String title;
    private final String content;
    private final String createdAt;
    private final Integer viewCnt;

    public QuestionResponse(Long questionId, String userName, String userLoginId, String title, String content, String createdAt, Integer viewCnt) {
        this.questionId = questionId;
        this.userName = userName;
        this.userLoginId = userLoginId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.viewCnt = viewCnt;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getViewCnt() {
        return viewCnt;
    }
}
