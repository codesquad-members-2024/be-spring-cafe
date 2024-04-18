package codesquad.springcafe.exceptions;

public class NoSuchUserException extends NoSuchException{
    public NoSuchUserException() {
        super("해당 유저를 찾을 수 없습니다");
    }
}
