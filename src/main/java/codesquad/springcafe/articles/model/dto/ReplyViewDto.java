package codesquad.springcafe.articles.model.dto;

import codesquad.springcafe.articles.model.Reply;

import java.time.LocalDate;

public class ReplyViewDto {
    private final Reply reply;
    private final boolean editRight;

    public ReplyViewDto(Reply reply, boolean editRight) {
        this.reply = reply;
        this.editRight = editRight;
    }

    public String getUserId() {
        return reply.getUserId();
    }

    public String getComment() {
        return reply.getComment();
    }

    public LocalDate getCreationDate() {
        return reply.getCreationDate();
    }

    public boolean hasEditRight() {
        return editRight;
    }

}
