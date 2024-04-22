package codesquad.springcafe.domain.comment;

import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.comment.DTO.CommentPostReq;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class ApiCommentController {
    private final CommentService commentService;

    @Autowired
    public ApiCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    public Comment create(@ModelAttribute CommentPostReq commentPostReq, HttpSession session) {
        SimpleUserInfo user = (SimpleUserInfo) session.getAttribute("loginUser");
        return commentService.postComment(commentPostReq, user);
    }
}
