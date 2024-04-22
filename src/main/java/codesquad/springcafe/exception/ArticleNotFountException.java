package codesquad.springcafe.exception;

public class ArticleNotFountException extends RuntimeException {

    public ArticleNotFountException() {
        super("해당 게시글이 존재하지 않습니다.");
    }

}
