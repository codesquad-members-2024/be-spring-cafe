package codesquad.springcafe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAuthenticationException extends NoSuchException{
    public NotAuthenticationException() {
        super("허용되지 않은 접근입니다");
    }
}
