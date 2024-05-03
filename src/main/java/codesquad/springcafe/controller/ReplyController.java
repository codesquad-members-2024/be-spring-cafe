package codesquad.springcafe.controller;

import codesquad.springcafe.exception.UnauthorizedAccessException;
import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static codesquad.springcafe.controller.UserController.LOGIN_USER_ID;

@Controller
@RequestMapping("/question/{questionId}/answers")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping
    public String createReply(@PathVariable Long questionId, @RequestParam String content, HttpSession session) {
        String userId = (String) session.getAttribute(LOGIN_USER_ID);
        replyService.createReply(userId, questionId, content);
        return "redirect:/question/" + questionId;
    }

    @DeleteMapping("/{replyId}")
    public String deleteReply(@PathVariable Long questionId, @PathVariable Long replyId, HttpSession session) {
        String userId = (String) session.getAttribute(LOGIN_USER_ID);
        replyService.validateReplyAuthor(replyId, userId);
        replyService.delete(replyId);
        return "redirect:/question/" + questionId;
    }
}
