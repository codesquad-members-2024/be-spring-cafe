package codesquad.springcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CommentAccessException extends RuntimeException {
    public CommentAccessException() {
        super("해당 댓글에 대한 접근권한이 없습니다.");
    }
}
