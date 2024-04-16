package codesquad.springcafe.exceptions;

public class NoSuchArticleException extends Exception{
    public NoSuchArticleException() {
        super("해당 글을 찾지 못했습니다");
    }
}
