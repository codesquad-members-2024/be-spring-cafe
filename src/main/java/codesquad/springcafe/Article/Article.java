package codesquad.springcafe.Article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {

    private final String writer;
    private final String title;
    private final String contents;
    private LocalDateTime time;
    private int articleNum;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = LocalDateTime.now();
    }

    public String getWriter() {
        return writer;
    }

    public String setWriter(String writer){return writer;}

    public String getTitle() {
        return title;
    }

    public String getContents() { return contents;}

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return time.format(formatter); }
    public LocalDateTime setTime(LocalDateTime time){
        return this.time = time;
    }

    public int getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(int articleNum) {
        this.articleNum = articleNum;
    }
}