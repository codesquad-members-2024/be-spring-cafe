package codesquad.springcafe.exception;

public class ArticleNotFoundException extends IllegalArgumentException {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
