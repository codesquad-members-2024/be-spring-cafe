package codesquad.springcafe.replies.model.dto;

public class ReplyCreationRequest {
    private final String writer;    // 작성자 [userId]
    private final String comment;   // 작성한 댓글

    public ReplyCreationRequest(String writer, String comment) {
        this.writer = writer;
        this.comment = comment;
    }

    public String getWriter() {
        return writer;
    }

    public String getComment() {
        return comment;
    }
}
