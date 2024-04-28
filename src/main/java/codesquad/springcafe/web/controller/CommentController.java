package codesquad.springcafe.web.controller;

import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.domain.user.User;
import codesquad.springcafe.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments/{articleId}/create")
    public String writeComment(
            @SessionAttribute(name = "loginUser", required = false) User loginUser,
            @PathVariable Long articleId,
            @RequestParam String content) {
        commentService.saveComment(new Comment(loginUser.getId(), articleId, loginUser.getUserId(), content, LocalDateTime.now()));
        return "redirect:/articles/" + articleId;
    }
}
