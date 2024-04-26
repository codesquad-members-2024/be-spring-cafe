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

    /**
     * 댓글을 등록한다.
     * @param replyForm
     * @param writerId
     * @param articleId
     */
    public void register(ReplyForm replyForm, String writerId, String articleId) {
        Reply reply = new Reply(
                replyForm.getContents(), writerId, replyForm.getTime(),
                false, Long.parseLong(articleId));
        replyRepository.add(reply);
    }

    /**
     * 해당하는 글에 달린 삭제되지 않은 댓글을 모두 가져온다.
     * @param articleId
     * @param writerId
     * @return
     */
    public List<ShowReply> getReplyBy(String articleId, String writerId) {
        List<ShowReply> result = replyRepository.getReplyBy(articleId);
        checkWriter(result, writerId);
        return checkDeletedReply(result);
    }

    /**
     * 현재 로그인한 사용자와 댓글의 작성자가 일치하는 경우 sameWriter 값을 변경한다.
     * 해당 값을 통해 수정, 삭제할 수 있는 기능을 화면에 표시하게 된다.
     * @param showReplies
     * @param writerId
     */
    private void checkWriter(List<ShowReply> showReplies, String writerId) {
        showReplies.stream()
                .filter(showReply -> showReply.getWriterId().equals(writerId))
                .forEach(ShowReply::changeSameWriter);
    }

    /**
     * 해당하는 글에 작성자가 아닌 다른 사용자의 댓글이 달려있는지 확인한다.
     * @param articleId
     * @param writer
     * @return
     */
    public boolean existOtherReply(String articleId, String writer) {
        List<ShowReply> result = replyRepository.getReplyBy(articleId);
        long count = result.stream()
                .filter(showReply -> !showReply.getWriterId().equals(writer))
                .count();
        return count > 0;
    }

    /**
     * 게시글에 달린 전체 댓글에서 삭제 상태가 아닌 댓글만 걸러낸다.
     * @param showReplies
     * @return
     */
    public List<ShowReply> checkDeletedReply(List<ShowReply> showReplies) {
        return showReplies.stream()
                .filter(showReply -> !showReply.getDeleted())
                .collect(Collectors.toList());
    }

    /**
     * 해당 댓글을 삭제한다.
     * @param replyId
     */
    public void deleteReply(String replyId) {
        replyRepository.delete(replyId);
    }

    /**
     * 해당 댓글을 가져온다.
     * @param replyId
     * @return
     */
    public Reply getReplyById(String replyId) {
        return replyRepository.getReplyById(replyId).orElseThrow(() -> new NotFoundException("해당하는 댓글이 없습니다"));
    }

    /**
     * 해당 게시글에 댓글이 존재하는지 여부를 확인한다.
     * @param articleId
     * @return
     */
    public boolean existReply(String articleId) {
        return replyRepository.getReplyBy(articleId).size() > 0;
    }

    /**
     * 해당 게시글을 삭제 시, 게시글에 달려있는 댓글의 삭제 상태를 true로 변경한다.
     * @param articleId
     * @return
     */
    public List<ShowReply> deleteAllReply(String articleId) {
        List<ShowReply> result = replyRepository.getReplyBy(articleId);
        result.stream()
                .forEach(showReply -> deleteReply(showReply.getId().toString()));
        return result;
    }

    /**
     * 해당 댓글을 수정한다.
     * @param editReply
     * @param replyId
     */
    public void editReply(EditReply editReply, String replyId) {
        editReply.setId(replyId);
        replyRepository.edit(editReply.getId(), editReply.getContents());
    }
}
