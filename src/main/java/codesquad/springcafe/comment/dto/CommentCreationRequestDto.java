package codesquad.springcafe.comment.dto;

public class CommentCreationRequestDto {
    private String contents;


    public CommentCreationRequestDto(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}
