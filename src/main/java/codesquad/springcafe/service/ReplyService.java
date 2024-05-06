package codesquad.springcafe.service;

import codesquad.springcafe.model.Reply;
import codesquad.springcafe.repository.reply.ReplyRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void upload(Reply newReply) {
        replyRepository.save(newReply);
    }

    public List<Reply> findAllByArticleId(Long articleId) {
        return replyRepository.getAllByArticleId(articleId);
    }

    public Reply findByArticleIdAndIndex(Long articleId, Long index) {
        Optional<Reply> targetReply = replyRepository.getByArticleIdAndIndex(articleId, index);
        return targetReply.orElse(null);
    }

    public void delete(Long articleId, Long index) {
        replyRepository.remove(articleId, index);
    }
}