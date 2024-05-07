package codesquad.springcafe.exception.db;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException(long commentId) {
        super(commentId + "라는 id를 가진 댓글은 존재하지 않습니다.");
    }
}