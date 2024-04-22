package codesquad.springcafe.reply;

import codesquad.springcafe.reply.database.ReplyDatabase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private ReplyDatabase replyDatabase;

    @Autowired
    public ReplyService(ReplyDatabase replyDatabase) {
        this.replyDatabase = replyDatabase;
    }

    public void save(ReplyCreateDto replyCreateDto) {
        replyDatabase.save(replyCreateDto.toEntity());
    }

    public List<Reply> findByArticleId(Long articleId) {
        return replyDatabase.findByArticleId(articleId);
    }

    public Reply findById(Long replyId) {
        return replyDatabase.findById(replyId);
    }

    public void delete(Long articleId) {
        replyDatabase.delete(articleId);
    }

    public boolean isAutehnticated(String userId, Long articleId) {
        return findById(articleId).getUserId().equals(userId);
    }
}
