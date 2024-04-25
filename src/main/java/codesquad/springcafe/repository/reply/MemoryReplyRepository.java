package codesquad.springcafe.repository.reply;

import codesquad.springcafe.model.Reply;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryReplyRepository implements ReplyRepository {
    private final List<Reply> replies;

    public MemoryReplyRepository() {
        this.replies = new ArrayList<>();
    }

    @Override
    public void save(Reply reply) {
        replies.add(reply);
    }

    @Override
    public Optional<Reply> getByArticleIdAndIndex(Long articleId, Long index) {
        return replies.stream()
            .filter(reply -> reply.isRightInfo(articleId, index))
            .findAny();
    }

    @Override
    public List<Reply> getAllByArticleId(Long articleId) {
        List<Reply> repliesByArticleId = replies.stream().filter(reply -> reply.isRightInfo(articleId))
            .sorted().toList();
        return new ArrayList<>(repliesByArticleId);
    }

    @Override
    public void remove(Long articleId, Long index) {
        Optional<Reply> targetReply = getByArticleIdAndIndex(articleId, index);
        targetReply.ifPresent(replies::remove);
    }
}
