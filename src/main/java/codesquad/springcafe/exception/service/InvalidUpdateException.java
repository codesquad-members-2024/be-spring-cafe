package codesquad.springcafe.exception.service;

public class InvalidUpdateException extends RuntimeException {
    public InvalidUpdateException(String msg) {
        super(msg);
    }
}