package codesquad.springcafe.dto;

public class AjaxTemplateReply {
    private String writerId;
    private String id;
    private String time;
    private String contents;
    private String articleId;

    public AjaxTemplateReply(String writerId, String id, String time, String contents,
                             String articleId) {
        this.articleId = articleId;
        this.id = id;
        this.time = time;
        this.writerId = writerId;
        this.contents = contents;
    }

    public String getWriterId() {
        return writerId;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getContents() {
        return contents;
    }

    public String getArticleId() {
        return articleId;
    }
}
