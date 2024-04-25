package codesquad.springcafe.service;

import codesquad.springcafe.dto.reply.ReplyInfoDTO;
import codesquad.springcafe.dto.reply.ReplyUploadDTO;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.repository.reply.ReplyRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public ReplyInfoDTO upload(ReplyUploadDTO replyUploadDTO) {
        Long nextIndex = getNextIndex(replyUploadDTO.getArticleId());
        Reply newReply = replyUploadDTO.toReply(nextIndex);
        replyRepository.save(newReply);
        return newReply.toDTO();
    }

    public List<ReplyInfoDTO> findAllByArticleId(Long articleId) {
        List<Reply> replies = replyRepository.getAllByArticleId(articleId);
        return replies.stream()
            .map(Reply::toDTO)
            .collect(Collectors.toList());
    }

    public ReplyInfoDTO findByArticleIdAndIndex(Long articleId, Long index) {
        Optional<Reply> targetReply = replyRepository.getByArticleIdAndIndex(articleId, index);
        return targetReply.map(Reply::toDTO).orElse(null);
    }

    private Long getNextIndex(Long articleId) {
        long maxIndex = replyRepository.getAllByArticleId(articleId).stream()
            .mapToLong(Reply::getIndex).max().orElse(0L);
        return ++maxIndex;
    }

    public void delete(Long articleId, Long index) {
        replyRepository.remove(articleId, index);
    }
}