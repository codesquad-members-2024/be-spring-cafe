package codesquad.springcafe.controller;

import codesquad.springcafe.dto.reply.ReplyInfoDTO;
import codesquad.springcafe.dto.reply.ReplyUploadDTO;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Long getLastIndex(Long articleId) {
        return replyService.findAllByArticleId(articleId).stream()
            .mapToLong(Reply::getIndex).max().orElse(0L);
    }
}