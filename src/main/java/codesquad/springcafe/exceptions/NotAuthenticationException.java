package codesquad.springcafe.exceptions;

public class NotAuthenticationException extends NoSuchException{
    public NotAuthenticationException() {
        super("허용되지 않은 접근입니다");
    }
}
