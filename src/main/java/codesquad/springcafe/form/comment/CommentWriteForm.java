package codesquad.springcafe.form.comment;

import jakarta.validation.constraints.NotBlank;

public class CommentWriteForm {
    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
