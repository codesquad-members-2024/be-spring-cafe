package codesquad.springcafe.exception;

public class ArticleNotFoundException extends Exception {
    public ArticleNotFoundException(long articleId) {
        super(articleId + "라는 게시글은 존재하지 않습니다.");
    }
}