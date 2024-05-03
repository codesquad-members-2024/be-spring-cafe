package codesquad.springcafe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchCommentException extends NoSuchException{

    public NoSuchCommentException() {
        super("해당 댓글을 찾지 못했습니다");
    }
}
