package codesquad.springcafe.domain.comment.model;

import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.global.annotation.AssociatedClass;
import codesquad.springcafe.global.model.BaseTime;

public class Comment extends BaseTime {
    private Long id;
    private Long userId;
    private Long questionId;
    private String content;
    private Boolean modified;
    private Boolean deleted;

    @AssociatedClass(User.class)
    private User user;

    public Comment() {

    }

    public Comment(User user, Long questionId, String content) {
        super();
        this.userId = user.getId();
        this.questionId = questionId;
        this.content = content;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Boolean getModified() {
        return modified;
    }

    public String getContent() {
        return content;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public User getUser() {
        return user;
    }

    public Comment update(String content) {
        this.content = content;
        this.modified = true;
        return this;
    }

    public Comment delete() {
        this.deleted = true;
        return this;
    }
}
