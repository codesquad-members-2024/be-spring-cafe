package codesquad.springcafe.error.exception;

public class ReplyNotFoundException extends RuntimeException {
    public ReplyNotFoundException(String message) {
        super(message);
    }
}