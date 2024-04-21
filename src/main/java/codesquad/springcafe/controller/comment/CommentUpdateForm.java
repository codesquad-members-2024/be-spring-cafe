package codesquad.springcafe.controller.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CommentUpdateForm {
    @NotNull
    private long id;
    @NotNull
    private long articleId;
    @NotEmpty
    private String author;
    @NotEmpty
    private String content;

    public CommentUpdateForm() {
    }

    public long getId() {
        return id;
    }

    public CommentUpdateForm setId(long id) {
        this.id = id;
        return this;
    }

    public long getArticleId() {
        return articleId;
    }

    public CommentUpdateForm setArticleId(long articleId) {
        this.articleId = articleId;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public CommentUpdateForm setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentUpdateForm setContent(String content) {
        this.content = content;
        return this;
    }
}
