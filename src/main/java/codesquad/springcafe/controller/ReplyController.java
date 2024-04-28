package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/articles")
public class ReplyController {
    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/{articleId}/reply")
    public String createReply(@PathVariable long articleId, @RequestParam String content, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Reply reply = new Reply(articleId, user.getUserId(), content);

        replyService.createReply(reply);

        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/{articleId}/reply/{replyId}")
    public String deleteReply(@PathVariable long articleId, @PathVariable long replyId, HttpSession session) {
        replyService.deleteReply(replyId);

        return "redirect:/articles/{articleId}";
    }
}