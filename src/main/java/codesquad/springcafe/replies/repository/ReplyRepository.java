package codesquad.springcafe.replies.repository;

import codesquad.springcafe.replies.model.Reply;

import java.util.ArrayList;
import java.util.Optional;

public interface ReplyRepository {

    Reply createReply(Reply reply);

    Optional<ArrayList<Reply>> getReplies(long articleId);

    Optional<Reply> findReplyById(long replyId);

    boolean deleteReply(long replyId);
}
