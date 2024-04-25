package codesquad.springcafe.exception;

public class ArticleAccessException extends RuntimeException {
    public ArticleAccessException(String message) {
        super(message);
    }
}
