package codesquad.springcafe.domain.comment.dto;

public class UpdateComment {

    private String identifier;
    private String writer;
    private String contents;

    public UpdateComment(String identifier, String writer, String contents) {
        this.identifier = identifier;
        this.writer = writer;
        this.contents = contents;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }
}
