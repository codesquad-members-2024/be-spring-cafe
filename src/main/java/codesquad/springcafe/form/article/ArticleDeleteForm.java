package codesquad.springcafe.form.article;

import jakarta.validation.constraints.NotBlank;

public class ArticleDeleteForm {
    @NotBlank
    private final String password;

    public ArticleDeleteForm(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
