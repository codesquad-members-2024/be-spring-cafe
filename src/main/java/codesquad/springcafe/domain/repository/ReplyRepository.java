package codesquad.springcafe.domain.repository;

import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.dto.ShowReply;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends DbRepository<Reply> {
    @Override
    void add(Reply reply);

    List<ShowReply> getReplyBy(String articleId);

    void delete(Reply reply);
}
