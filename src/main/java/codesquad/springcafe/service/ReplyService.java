package codesquad.springcafe.service;

import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.domain.repository.ReplyRepository;
import codesquad.springcafe.dto.EditReply;
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
        return checkDeletedReply(result);
    }

    // 해당 값을 순회하면서 일단 바꿔줘야 한다 지금 글쓴이가 쓴 댓글인지의 여부를 체크해서
    private void checkWriter(List<ShowReply> showReplies, String writerId) {
        showReplies.stream()
                .filter(showReply -> showReply.getWriterId().equals(writerId))
                .forEach(ShowReply::changeSameWriter);
    }

    public boolean existOtherReply(String articleId, String writer) {
        List<ShowReply> result = replyRepository.getReplyBy(articleId);
        long count = result.stream()
                .filter(showReply -> !showReply.getWriterId().equals(writer))
                .count();
        return count > 0;
    }

    // 삭제값인 경우 반환할 리스트에서 제거한다
    public List<ShowReply> checkDeletedReply(List<ShowReply> showReplies) {
        return showReplies.stream()
                .filter(showReply -> !showReply.getDeleted())
                .collect(Collectors.toList());
    }

    public void deleteReply(String replyId) {
        replyRepository.delete(replyId);
    }

    public Reply getReplyById(String replyId) {
        return replyRepository.getReplyById(replyId).orElseThrow(() -> new NotFoundException("해당하는 댓글이 없습니다"));
    }

    public boolean existReply(String articleId) {
        return replyRepository.getReplyBy(articleId).size() > 0;
    }

    // 해당 articleid를 가진 댓글들의 상태를 모두 삭제로 바꾼다
    public List<ShowReply> deleteAllReply(String articleId) {
        List<ShowReply> result = replyRepository.getReplyBy(articleId);
        result.stream()
                .forEach(showReply -> deleteReply(showReply.getId().toString()));
        return result;
    }

    public void editReply(EditReply editReply, String replyId) {
        editReply.setId(replyId);
        replyRepository.edit(editReply.getId(), editReply.getContents());
    }
}
