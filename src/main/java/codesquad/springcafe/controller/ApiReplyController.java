package codesquad.springcafe.controller;

import codesquad.springcafe.dto.reply.ReplyInfoDTO;
import codesquad.springcafe.dto.reply.ReplyUploadDTO;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.model.Result;
import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles/{articleId}/replies")
public class ApiReplyController {

    private final ReplyService replyService;

    @Autowired
    public ApiReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("")
    public ReplyInfoDTO upload(@ModelAttribute ReplyUploadDTO replyUploadDTO, HttpSession session) {
        Long lastIndex = getLastIndex(replyUploadDTO.getArticleId());
        Reply newReply = replyUploadDTO.toReply(++lastIndex);
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !newReply.isWrittenBy(loggedInUser)) {
            return null;
        }
        replyService.upload(newReply);
        return newReply.toDTO();
    }

    @GetMapping("/count")
    public Long getTotalCount(@PathVariable Long articleId) {
        return (long) replyService.findAllByArticleId(articleId).size();
    }

    @DeleteMapping("/{index}")
    public Result delete(@PathVariable("articleId") Long articleId, @PathVariable("index") Long index, HttpSession session) {
        Reply targetReply = replyService.findByArticleIdAndIndex(articleId, index);
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        boolean success = loggedInUser != null && targetReply.isWrittenBy(loggedInUser) && replyService.delete(articleId, index);
        if (success) {
            return Result.ok();
        }
        return Result.fail("다른 사용자의 댓글을 삭제할 수 없습니다.");
    }

    private Long getLastIndex(Long articleId) {
        return replyService.findAllByArticleId(articleId).stream()
            .mapToLong(Reply::getIndex).max().orElse(0L);
    }
}