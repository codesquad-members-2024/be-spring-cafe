package codesquad.springcafe.controller;

import java.beans.ConstructorProperties;

public class ArticleForm {
    private String writer;
    private String title;
    private String contents;


    @ConstructorProperties({"writer", "title", "contents"})
    public ArticleForm(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }
}
