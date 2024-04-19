package codesquad.springcafe.exception.service;

public class UserInvalidUpdateException extends InvalidUpdateException {
    public UserInvalidUpdateException() {
        super("다른 사용자의 개인정보는 수정할 수 없습니다.");
    }
}