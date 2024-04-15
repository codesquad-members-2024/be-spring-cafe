package codesquad.springcafe;

public class Article {
    private String writer;
    private String title;
    private String content;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

//    글쓴이를 수정하지 않기 때문에 제거
//    public void setWriter(String writer) {
//        this.writer = writer;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "writer : " + writer + ", title : " + title + ", content : " + content;
    }
}
