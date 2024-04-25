package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.ReplyForm;
import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    // 댓글 등록
    @PostMapping("/reply/{articleId}")
    public String register(HttpSession session, @PathVariable("articleId") String articleId, ReplyForm replyForm) {
        User writer = (User) session.getAttribute("sessionUser");
        replyService.register(replyForm, writer.getUserId(), articleId);
        return "redirect:/article/{articleId}";
    }

    // 댓글 삭제
    @DeleteMapping("/reply")
    public void delete(HttpSession session) {

    }
}
