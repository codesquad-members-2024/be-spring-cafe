package codesquad.springcafe.repository;

import codesquad.springcafe.model.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {

    Reply save(Reply reply);

    Optional<Reply> findById(Long replyId);

    List<Reply> findRepliesByArticleId(Long articleId);

    void delete(Long replyId);

}
