package codesquad.springcafe.controller.comment;

public class CommentForm {
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
