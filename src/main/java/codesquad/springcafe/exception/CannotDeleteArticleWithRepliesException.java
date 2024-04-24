package codesquad.springcafe.exception;

public class CannotDeleteArticleWithRepliesException extends RuntimeException {

    public CannotDeleteArticleWithRepliesException() {
        super("댓글이 있는 게시글은 삭제할 수 없습니다.");
    }
}
