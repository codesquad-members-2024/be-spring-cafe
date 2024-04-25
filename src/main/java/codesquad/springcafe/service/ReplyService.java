package codesquad.springcafe.service;

import codesquad.springcafe.exception.ReplyNotFound;
import codesquad.springcafe.exception.UnauthorizedAccessException;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void createReply(String userId, Long articleId, String content) {
        Reply reply = new Reply(articleId, userId, content);
        replyRepository.save(reply);
    }

    public List<Reply> findRepliesByArticle(Long articleId) {
        return replyRepository.findRepliesByArticleId(articleId);
    }

    public void delete(Long replyId) {
        replyRepository.delete(replyId);
    }

    public Reply findById(Long replyId) {
        Optional<Reply> optionalReply = replyRepository.findById(replyId);
        if (optionalReply.isPresent()) {
            return optionalReply.get();
        }
        throw new ReplyNotFound();
    }

    public void validateReplyAuthor(Long replyId, String userId) {
        Reply reply = findById(replyId);
        if (!userId.equals(reply.getAuthor())) {
            throw new UnauthorizedAccessException("본인의 댓글만 수정/삭제가 가능합니다.");
        }
    }
}
