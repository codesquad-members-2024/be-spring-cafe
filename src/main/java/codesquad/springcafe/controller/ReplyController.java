package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.EditReply;
import codesquad.springcafe.dto.ReplyForm;
import codesquad.springcafe.service.ReplyService;
import codesquad.springcafe.service.UserService;
import codesquad.springcafe.service.validator.UserValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final UserService userService;
    private final UserValidator userValidator;

    public ReplyController(ReplyService replyService, UserService userService, UserValidator userValidator) {
        this.replyService = replyService;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    // 댓글 등록
    @PostMapping("/reply/{articleId}")
    public String register(HttpSession session, @PathVariable("articleId") String articleId, ReplyForm replyForm) {
        User writer = (User) session.getAttribute("sessionUser");
        replyService.register(replyForm, writer.getUserId(), articleId);
        return "redirect:/article/{articleId}";
    }

    // 댓글 삭제
    @DeleteMapping("/reply/{articleId}/{replyId}")
    public String delete(HttpSession session, @PathVariable("replyId") String replyId) {
        User expected = (User) session.getAttribute("sessionUser");
        String replyWriterId = replyService.getReplyById(replyId).getWriterId();
        User actual = userService.getUserById(replyWriterId);
        userValidator.validSameUser(expected, actual);

        replyService.deleteReply(replyId);
        return "redirect:/article/{articleId}";
    }

    // 댓글 수정
    @PutMapping("/reply/{articleId}/{replyId}")
    public String edit(HttpSession session, @PathVariable("replyId") String replyId, EditReply editReply) {
        User expected = (User) session.getAttribute("sessionUser");
        String replyWriterId = replyService.getReplyById(replyId).getWriterId();
        User actual = userService.getUserById(replyWriterId);
        userValidator.validSameUser(expected, actual);

        replyService.editReply(editReply, replyId);
        return "redirect:/article/{articleId}";
    }

    // 댓글 수정창으로 이동
    @GetMapping("/reply/{articleId}/{replyId}")
    public String showEditReplyForm(Model model, @PathVariable("replyId") String replyId, @PathVariable("articleId") String articleId) {
        model.addAttribute("replyId", replyId);
        model.addAttribute("articleId", articleId);
        return "article/editReplyForm";
    }
}
