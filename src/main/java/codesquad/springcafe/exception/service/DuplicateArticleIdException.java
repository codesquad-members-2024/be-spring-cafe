package codesquad.springcafe.exception.service;

public class DuplicateArticleIdException extends DuplicateException {
    public DuplicateArticleIdException(long articleId) {
        super(articleId + "라는 id를 가진 게시글은 이미 존재합니다.");
    }
}