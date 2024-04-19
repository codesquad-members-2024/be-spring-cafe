package codesquad.springcafe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchUserException extends NoSuchException{
    public NoSuchUserException() {
        super("해당 유저를 찾을 수 없습니다");
    }
}
