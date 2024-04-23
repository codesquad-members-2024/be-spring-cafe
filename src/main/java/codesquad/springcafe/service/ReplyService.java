package codesquad.springcafe.service;

import codesquad.springcafe.model.Reply;
import codesquad.springcafe.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
