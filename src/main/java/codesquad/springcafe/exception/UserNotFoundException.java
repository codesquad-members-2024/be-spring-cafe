package codesquad.springcafe.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String userID) {
        super(userID + "라는 id를 가진 사용자는 존재하지 않습니다.");
    }
}