package codesquad.springcafe.controller.article;

import jakarta.validation.constraints.NotEmpty;

public class ArticleForm {
    @NotEmpty
    private String title;
    @NotEmpty
    private String contents;
    @NotEmpty
    private String createdBy;

    public ArticleForm() {
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ArticleForm setTitle(String title) {
        this.title = title;
        return this;
    }

    public ArticleForm setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public ArticleForm setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }
}
