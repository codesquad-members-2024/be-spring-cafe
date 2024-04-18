package codesquad.springcafe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchArticleException extends NoSuchException{
    public NoSuchArticleException() {
        super("해당 글을 찾지 못했습니다");
    }
}
