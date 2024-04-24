package codesquad.springcafe.exception.service;

public class UserNotJoinedException extends Exception {
    public UserNotJoinedException(String userID) {
        super(userID + "라는 id를 가진 사용자는 가입된 사용자가 아닙니다.");
    }
}
