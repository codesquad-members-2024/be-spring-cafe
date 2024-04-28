package codesquad.springcafe.repository.reply;

import codesquad.springcafe.domain.Reply;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository {
    void createReply(Reply reply);

    void deleteReply(long replyId);

    List<Reply> findAllReplies(long articleId);

    Optional<Reply> findByReplyId(long replyId);
}