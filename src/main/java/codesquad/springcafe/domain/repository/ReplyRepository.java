package codesquad.springcafe.domain.repository;

import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.dto.ShowReply;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends DbRepository<Reply> {
    @Override
    Reply add(Reply reply);

    List<ShowReply> getReplyBy(String articleId);

    Optional<Reply> getReplyById(String replyId);

    void delete(String replyId);

    void edit(String articleId, String contents);

    Optional<Integer> getReplyCount(String articleId);
}
