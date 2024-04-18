package codesquad.springcafe.form.article;

import jakarta.validation.constraints.NotBlank;

public class ArticleWriteForm {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public ArticleWriteForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
