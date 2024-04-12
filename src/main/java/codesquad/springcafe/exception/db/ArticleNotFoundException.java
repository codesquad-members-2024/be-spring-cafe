package codesquad.springcafe.exception.db;

public class ArticleNotFoundException extends NotFoundException {
    public ArticleNotFoundException(long articleId) {
        super(articleId + "라는 id를 가진 게시글은 존재하지 않습니다.");
    }
}