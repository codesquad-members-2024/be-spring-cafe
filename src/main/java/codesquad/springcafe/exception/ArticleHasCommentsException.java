package codesquad.springcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ArticleHasCommentsException extends RuntimeException {
    public ArticleHasCommentsException(Long id) {
        super("해당 게시물에 다른 사용자의 댓글이 존재하여 삭제할 수 없습니다. id: " + id);
    }
}
