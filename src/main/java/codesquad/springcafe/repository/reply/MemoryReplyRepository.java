package codesquad.springcafe.repository.reply;

import codesquad.springcafe.domain.Reply;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryReplyRepository implements ReplyRepository {

    @Override
    public void createReply(Reply reply) {

    }

    @Override
    public void deleteReply(long replyId) {

    }

    @Override
    public List<Reply> findRepliesByArticleId(long articleId) {
        return null;
    }

    @Override
    public Optional<Reply> findByReplyId(long replyId) {
        return Optional.empty();
    }
}
