package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Result;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.AjaxTemplateReply;
import codesquad.springcafe.dto.ReplyForm;
import codesquad.springcafe.service.ReplyService;
import codesquad.springcafe.service.UserService;
import codesquad.springcafe.service.validator.UserValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyRestController {

    private final ReplyService replyService;
    private final UserService userService;
    private final UserValidator userValidator;

    public ReplyRestController(ReplyService replyService, UserService userService, UserValidator userValidator) {
        this.replyService = replyService;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping("/api/reply/{articleId}")
    public AjaxTemplateReply register(HttpSession session, @PathVariable("articleId") String articleId, @RequestBody ReplyForm replyForm) {
        User writer = (User) session.getAttribute("sessionUser");
        return replyService.register(replyForm, writer.getUserId(), articleId);
    }

    @DeleteMapping("/api/reply/{articleId}/{replyId}")
    public ResponseEntity<Result> delete(HttpSession session, @PathVariable("replyId") String replyId) {
        User expected = (User) session.getAttribute("sessionUser");
        String replyWriterId = replyService.getReplyById(replyId).getWriterId();
        User actual = userService.getUserById(replyWriterId);
        userValidator.validSameUser(expected, actual);

        try {
            replyService.deleteReply(replyId);
            return ResponseEntity.ok().body(Result.ok());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.fail("댓글 삭제를 실패했습니다."));
        }
    }
}
