package codesquad.springcafe.service;

import codesquad.springcafe.model.Reply;
import codesquad.springcafe.repository.reply.ReplyRepository;
import codesquad.springcafe.util.PageRequest;
import codesquad.springcafe.util.PaginationHelper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void upload(Reply newReply) {
        replyRepository.save(newReply);
    }

    public List<Reply> findAllByArticleId(Long articleId) {
        return replyRepository.getAllByArticleId(articleId);
    }

    public Reply findByArticleIdAndIndex(Long articleId, Long index) {
        Optional<Reply> targetReply = replyRepository.getByArticleIdAndIndex(articleId, index);
        return targetReply.orElse(null);
    }

    public List<Reply> findAllByArticleIdAndPage(Long articleId, PageRequest pageRequest) {
        List<Reply> allReplies = replyRepository.getAllByArticleId(articleId);
        Collections.sort(allReplies);

        PaginationHelper<Reply> paginationHelper = new PaginationHelper<>(allReplies, pageRequest.getSize());
        return paginationHelper.getPage(pageRequest.getNumber());
    }

    public boolean hasMoreComments(Long articleId, PageRequest pageRequest) {
        List<Reply> allReplies = replyRepository.getAllByArticleId(articleId);

        PaginationHelper<Reply> paginationHelper = new PaginationHelper<>(allReplies, pageRequest.getSize());
        return paginationHelper.hasMorePages(pageRequest.getNumber());
    }

    public boolean delete(Long articleId, Long index) {
        return replyRepository.remove(articleId, index);
    }
}