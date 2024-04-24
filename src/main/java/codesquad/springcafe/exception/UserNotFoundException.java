package codesquad.springcafe.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super("ID가 " + userId + "인 사용자가 존재하지 않습니다.");
    }

}
