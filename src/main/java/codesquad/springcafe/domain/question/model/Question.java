package codesquad.springcafe.domain.question.model;

import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.global.model.BaseTime;

import java.time.LocalDateTime;

public class Question extends BaseTime {
    private Long id;
    private User user;
    private String title;
    private String content;
    private Integer viewCnt;

    public Question(User user, String title, String content, Integer viewCnt, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(createdAt, modifiedAt);
        this.user = user;
        this.title = title;
        this.content = content;
        this.viewCnt = viewCnt;
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

    public Integer getViewCnt() {
        return viewCnt;
    }

    public void viewCntUp() {
        viewCnt++;
    }
}
