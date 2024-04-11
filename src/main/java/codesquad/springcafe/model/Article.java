package codesquad.springcafe.model;

public class Article {

    private String writer;
    private String title;
    private String contents;

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Article{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + contents + '\'' +
                '}';
    }
}
