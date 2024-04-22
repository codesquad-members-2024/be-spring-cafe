package codesquad.springcafe.reply.database;

import codesquad.springcafe.reply.Reply;
import java.util.List;

public interface ReplyDatabase {

    void save(Reply reply);

    List<Reply> findByArticleId(Long articleId);

    Reply findById(Long replyId);

    void delete(Long reply);
}
