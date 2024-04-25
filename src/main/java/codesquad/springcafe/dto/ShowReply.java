package codesquad.springcafe.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowReply {
    private String writerId;
    private LocalDateTime time;
    private String contents;
    private boolean isSameWriter;
    private Long id;
    private boolean deleted;

    public ShowReply(String writerId, LocalDateTime time, String contents, boolean deleted) {
        this.writerId = writerId;
        this.time = time;
        this.contents = contents;
        this.deleted = deleted;
        this.isSameWriter = false;
    }
    public String getWriterId() {
        return writerId;
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
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

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getDeleted() {
        return deleted;
    }
}
