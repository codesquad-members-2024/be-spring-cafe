package codesquad.springcafe.domain.question.model;

import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.global.model.BaseTime;

import java.time.LocalDateTime;

public class Question extends BaseTime {
    private Long id;
    private User user;
    private String title;
    private String content;


    public Question(User user, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(createdAt, modifiedAt);
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
