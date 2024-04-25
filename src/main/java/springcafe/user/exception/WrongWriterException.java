package springcafe.user.exception;

public class WrongWriterException extends RuntimeException{
    public WrongWriterException() {
    }

    public WrongWriterException(String message) {
        super(message);
    }
}
