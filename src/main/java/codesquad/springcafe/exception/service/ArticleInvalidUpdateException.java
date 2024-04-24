package codesquad.springcafe.exception.service;

public class ArticleInvalidUpdateException extends InvalidUpdateException {
    public ArticleInvalidUpdateException() {
        super("다른 사용자의 게시글은 수정할 수 없습니다.");
    }
}