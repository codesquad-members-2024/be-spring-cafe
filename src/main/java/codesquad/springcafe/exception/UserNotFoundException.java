package codesquad.springcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String nickname) {
        super("닉네임과 일치하는 유저를 찾을 수 없습니다. 닉네임: " + nickname);
    }
}
