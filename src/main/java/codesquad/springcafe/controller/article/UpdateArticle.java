package codesquad.springcafe.controller.article;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdateArticle {
    @NotNull
    private long id;
    @NotEmpty
    private String createdBy;
    @NotEmpty
    private String title;
    private String contents;

    public UpdateArticle() {
    }

    public long getId() {
        return id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public UpdateArticle setId(long id) {
        this.id = id;
        return this;
    }

    public UpdateArticle setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public UpdateArticle setTitle(String title) {
        this.title = title;
        return this;
    }

    public UpdateArticle setContents(String contents) {
        this.contents = contents;
        return this;
    }
}
