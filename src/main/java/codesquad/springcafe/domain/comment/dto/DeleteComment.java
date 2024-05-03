package codesquad.springcafe.domain.comment.dto;

public class DeleteComment {

    private String identifier;
    private String writtenArticle;

    public DeleteComment(String identifier, String writtenArticle) {
        this.identifier = identifier;
        this.writtenArticle = writtenArticle;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getWrittenArticle() {
        return writtenArticle;
    }
}
