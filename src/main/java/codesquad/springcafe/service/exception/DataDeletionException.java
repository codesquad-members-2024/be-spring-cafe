package codesquad.springcafe.service.exception;

public class DataDeletionException extends RuntimeException {
    public DataDeletionException() {
        super();
    }

    public DataDeletionException(String message) {
        super(message);
    }

    public DataDeletionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataDeletionException(Throwable cause) {
        super(cause);
    }

    protected DataDeletionException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
