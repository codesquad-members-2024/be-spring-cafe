package codesquad.springcafe.service;

import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.error.exception.ReplyNotFoundException;
import codesquad.springcafe.repository.reply.ReplyRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private static final Logger logger = LoggerFactory.getLogger(ReplyService.class);
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void createReply(Reply reply) {
        replyRepository.createReply(reply);
        logger.debug("ID {} 댓글 생성", reply.getReplyId());
    }

    public void deleteReply(long replyId) {
        replyRepository.deleteReply(replyId);
        logger.debug("ID {} 댓글 삭제", replyId);
    }

    public List<Reply> findAllReplies(long articleId) {
        return replyRepository.findAllReplies(articleId);
    }

    public Reply findByReplyId(long replyId) {
        return replyRepository.findByReplyId(replyId).orElseThrow(() -> new ReplyNotFoundException("댓글 없음"));
    }
}