package codesquad.springcafe.domain.comment.controller;

import codesquad.springcafe.domain.comment.data.CommentRequest;
import codesquad.springcafe.domain.comment.service.CommentService;
import codesquad.springcafe.domain.user.data.UserCredentials;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @PostMapping("/comment")
    public String createComment(HttpSession httpSession,
                                @Valid @ModelAttribute CommentRequest commentRequest,
                                RedirectAttributes redirectAttributes) {
        Long userId = getUserCredentials(httpSession).getUserId();
        commentService.createComment(userId, commentRequest);

        redirectAttributes.addAttribute("questionId", commentRequest.getQuestionId());
        return "redirect:/question/{questionId}";
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(HttpSession httpSession,
                                @RequestParam("questionId") Long questionId,
                                @PathVariable("commentId") Long commentId,
                                RedirectAttributes redirectAttributes) {
        Long userId = getUserCredentials(httpSession).getUserId();
        commentService.deleteComment(userId, questionId, commentId);

        redirectAttributes.addAttribute("questionId", questionId);
        return "redirect:/question/{questionId}";
    }

    private UserCredentials getUserCredentials(HttpSession httpSession) {
        Object userCredentials = httpSession.getAttribute("userCredentials");
        if (userCredentials == null) {
            throw new IllegalStateException("인증이 필요한 요청입니다.");
        }
        return (UserCredentials) userCredentials;
    }
}
