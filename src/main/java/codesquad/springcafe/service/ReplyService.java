package codesquad.springcafe.service;

import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.domain.repository.ReplyRepository;
import codesquad.springcafe.dto.ReplyForm;
import codesquad.springcafe.dto.ShowReply;
import codesquad.springcafe.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void register(ReplyForm replyForm, String writerId, String articleId) {
        Reply reply = new Reply(
                replyForm.getContents(), writerId, replyForm.getTime(),
                false, Long.parseLong(articleId));
        replyRepository.add(reply);
    }

    // 해당하는 글에 대한 모든 댓글을 가져온다
    public List<ShowReply> getReplyBy(String articleId, String writerId) {
        List<ShowReply> result = replyRepository.getReplyBy(articleId);
        checkWriter(result, writerId);
        return result;
    }

    // 해당 값을 순회하면서 일단 바꿔줘야 한다 지금 글쓴이가 쓴 댓글인지의 여부를 체크해서
    private void checkWriter(List<ShowReply> showReplies, String writerId) {
        showReplies.stream()
                .filter(showReply -> showReply.getWriterId().equals(writerId))
                .forEach(ShowReply::changeSameWriter);
    }
}
