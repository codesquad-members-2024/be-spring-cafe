package codesquad.springcafe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CanNotLoginException extends RuntimeException{

    public CanNotLoginException() {
        super("아이디 또는 비밀번호가 잘못되었습니다");
    }

    public CanNotLoginException(String message) {
        super(message);
    }
}
