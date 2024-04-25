package codesquad.springcafe.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowReply {
    private String writerId;
    private LocalDateTime time;
    private String contents;
    private boolean isSameWriter;

    public ShowReply(String writerId, LocalDateTime time, String contents) {
        this.writerId = writerId;
        this.time = time;
        this.contents = contents;
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
}
