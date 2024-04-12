package codesquad.springcafe.exception.db;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String userID) {
        super(userID + "라는 id를 가진 사용자는 존재하지 않습니다.");
    }
}