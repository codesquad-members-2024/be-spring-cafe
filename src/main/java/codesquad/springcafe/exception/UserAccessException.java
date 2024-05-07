package codesquad.springcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAccessException extends RuntimeException {
    public UserAccessException() {
        super("해당 유저에 대한 접근권한이 없습니다.");
    }
}
