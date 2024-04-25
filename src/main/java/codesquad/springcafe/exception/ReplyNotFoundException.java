package codesquad.springcafe.exception;

public class ReplyNotFoundException extends RuntimeException {
    public ReplyNotFoundException(String message) {
        super(message);
    }
}
