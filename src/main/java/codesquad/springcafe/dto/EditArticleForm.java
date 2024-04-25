package codesquad.springcafe.dto;

public class EditArticleForm {
    private String title;
    private String contents;

    public EditArticleForm(String title, String contents) {
        this.contents = contents;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
