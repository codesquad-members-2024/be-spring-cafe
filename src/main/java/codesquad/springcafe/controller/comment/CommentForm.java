package codesquad.springcafe.controller.comment;

import jakarta.validation.constraints.NotEmpty;

public class CommentForm {
    @NotEmpty
    private String content;

    public CommentForm() {
    }

    public String getContent() {
        return content;
    }

    public CommentForm setContent(String content) {
        this.content = content;
        return this;
    }
}
