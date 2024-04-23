package codesquad.springcafe.controller;

import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}
