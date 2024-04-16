package codesquad.springcafe.form.comment;

import jakarta.validation.constraints.NotBlank;

public class CommentWriteForm {
    @NotBlank
    private final String content;

    public CommentWriteForm(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
