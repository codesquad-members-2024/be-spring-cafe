package codesquad.springcafe.exception.service;

public class DuplicateUserIdException extends DuplicateException {
    public DuplicateUserIdException(String userID) {
        super(userID + "라는 userId를 가진 사용자는 이미 존재합니다.");
    }
}