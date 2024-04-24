package springcafe.article.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ArticleForm {

    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 200)
    String title;
    @NotEmpty(message = "내용은 필수 항목입니다.")
    String contents;

    public ArticleForm(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }


}
