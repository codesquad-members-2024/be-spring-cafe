package codesquad.springcafe.replies.service;

import codesquad.springcafe.replies.model.Reply;
import codesquad.springcafe.replies.model.dto.ReplyCreationRequest;
import codesquad.springcafe.replies.model.dto.ReplyViewDto;
import codesquad.springcafe.exception.ReplyNotFoundException;
import codesquad.springcafe.replies.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public Reply createReply(long articleId, ReplyCreationRequest replyCreationRequest) {
        Reply reply = new Reply(articleId, replyCreationRequest.getWriter(), replyCreationRequest.getComment());

        return replyRepository.createReply(reply);
    }

    public ArrayList<ReplyViewDto> getReplies(String sessionedUserId, long articleId) {
        ArrayList<Reply> replies = replyRepository.getReplies(articleId).orElseThrow(() -> new ReplyNotFoundException("댓글을 찾을 수 없습니다."));

        ArrayList<ReplyViewDto> replyViews = new ArrayList<>(replies.size());

        for (Reply reply : replies) {
            boolean editRight = reply.getUserId().equals(sessionedUserId);
            ReplyViewDto replyViewDto = new ReplyViewDto(reply, editRight);
            replyViews.add(replyViewDto);
        }

        return replyViews;
    }

    public Reply findReplyById(long replyId) {
        return replyRepository.findReplyById(replyId).orElseThrow(() -> new ReplyNotFoundException("해당하는 댓글을 찾을 수 없습니다."));
    }

    public boolean deleteReply(long replyId) {
        return replyRepository.deleteReply(replyId);
    }
}
