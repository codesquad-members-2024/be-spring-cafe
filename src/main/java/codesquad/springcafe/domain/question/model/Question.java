package codesquad.springcafe.domain.question.model;

import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.global.annotation.AssociatedClass;
import codesquad.springcafe.global.model.BaseTime;

public class Question extends BaseTime {
    private Long id;
    @AssociatedClass(User.class)
    private User user;
    private String title;
    private String content;
    private Integer viewCnt;
    private Boolean modified;
    private Boolean deleted;


    public Question() {

    }

    public Question(User user, String title, String content, Integer viewCnt) {
        super();
        this.title = title;
        this.content = content;
        this.viewCnt = viewCnt;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public Boolean getModified() {
        return modified;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public User getUser() {
        return user;
    }

    public Question update(String title, String content) {
        this.title = title;
        this.content = content;
        this.modified = true;
        return this;
    }

    public Question delete() {
        this.deleted = true;
        return this;
    }
}
