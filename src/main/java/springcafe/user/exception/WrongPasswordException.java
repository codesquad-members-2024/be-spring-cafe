package springcafe.user.exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
