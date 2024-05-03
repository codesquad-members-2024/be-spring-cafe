package codesquad.springcafe.dto;

public class EditReply {
    private String contents;
    private String id;

    public EditReply() {
    }

    public String getContents() {
        return contents;
    }

    public String getId() {
        return id;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setId(String id) {
        this.id = id;
    }
}
