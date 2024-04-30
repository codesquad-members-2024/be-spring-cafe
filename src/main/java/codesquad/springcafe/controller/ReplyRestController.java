package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.AjaxTemplateReply;
import codesquad.springcafe.dto.ReplyForm;
import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyRestController {

    private final ReplyService replyService;

    public ReplyRestController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/api/reply/{articleId}")
    public AjaxTemplateReply register(HttpSession session, @PathVariable("articleId") String articleId, @RequestBody ReplyForm replyForm) {
        User writer = (User) session.getAttribute("sessionUser");
        return replyService.register(replyForm, writer.getUserId(), articleId);
    }
}
