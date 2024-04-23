package codesquad.springcafe.exception;

public class ReplyNotFound extends RuntimeException {

    public ReplyNotFound() {
        super("존재하지 않는 댓글입니다.");
    }
}
