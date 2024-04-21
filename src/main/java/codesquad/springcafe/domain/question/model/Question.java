package codesquad.springcafe.domain.question.model;

import codesquad.springcafe.global.model.BaseTime;

public class Question extends BaseTime {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Integer viewCnt;
    private Boolean deleted;

    public Question() {

    }

    public Question(Long userId, String title, String content, Integer viewCnt) {
        super();
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public Question update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public Question delete() {
        this.deleted = true;
        return this;
    }
}
