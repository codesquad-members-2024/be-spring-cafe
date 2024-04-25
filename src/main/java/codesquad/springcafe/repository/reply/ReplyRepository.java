package codesquad.springcafe.repository.reply;

import codesquad.springcafe.model.Reply;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository {
    void save(Reply reply);

    Optional<Reply> getByArticleIdAndIndex(Long articleId, Long index);

    List<Reply> getAllByArticleId(Long articleId);
}