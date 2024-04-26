package codesquad.springcafe.controller;

import codesquad.springcafe.dto.reply.ReplyUploadDTO;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("articles/{articleId}/replies")
    public String upload(@ModelAttribute("reply") ReplyUploadDTO replyUploadDTO) {
        Long lastIndex = getLastIndex(replyUploadDTO.getArticleId());
        Reply newReply = replyUploadDTO.toReply(++lastIndex);
        replyService.upload(newReply);
        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("articles/{articleId}/replies/{index}")
    public String delete(@PathVariable Long articleId, @PathVariable Long index, HttpSession session) {
        Reply reply = replyService.findByArticleIdAndIndex(articleId, index);
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (!reply.isWrittenBy(loggedInUser)) {
            return "/reply/delete_failed";
        }
        replyService.delete(articleId, index);
        return "redirect:/articles/{articleId}";
    }

    private Long getLastIndex(Long articleId) {
        return replyService.findAllByArticleId(articleId).stream()
            .mapToLong(Reply::getIndex).max().orElse(0L);
    }
}