package codesquad.springcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ArticleAccessException extends RuntimeException {
    public ArticleAccessException() {
        super("해당 게시글에 대한 접근권한이 없습니다.");
    }
}
