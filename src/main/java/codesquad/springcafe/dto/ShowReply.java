package codesquad.springcafe.dto;

public class ShowReply {
    private String writerId;
    private String time;
    private String contents;
    private boolean isSameWriter;
    private Long id;

    public ShowReply(String writerId, String time, String contents) {
        this.writerId = writerId;
        this.time = time;
        this.contents = contents;
        this.isSameWriter = false;
    }
    public String getWriterId() {
        return writerId;
    }

    public String getTime() {
        return time;
    }

    public String getContents() {
        return contents;
    }

    public boolean isSameWriter() {
        return isSameWriter;
    }

    public void changeSameWriter() {
        isSameWriter = !isSameWriter;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
