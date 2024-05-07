package codesquad.springcafe.exception;

public class ArticleCreationException extends RuntimeException {
    public ArticleCreationException(String message) {
        super(message);
    }
}
