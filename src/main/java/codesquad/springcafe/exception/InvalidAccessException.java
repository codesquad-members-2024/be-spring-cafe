package codesquad.springcafe.exception;

public class InvalidAccessException extends RuntimeException {
    private String message;

    public InvalidAccessException(String message) {
        this.message = message;
    }
}
