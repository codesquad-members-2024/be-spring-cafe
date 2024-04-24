package codesquad.springcafe.domain.comment;

import codesquad.springcafe.api.ApiResponse;
import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.comment.DTO.CommentPostReq;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class ApiCommentController {
    private final CommentService commentService;

    @Autowired
    public ApiCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment create(@ModelAttribute CommentPostReq commentPostReq, HttpSession session) {
        SimpleUserInfo user = (SimpleUserInfo) session.getAttribute("loginUser");
        return commentService.postComment(commentPostReq, user);
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable int id, HttpSession session) {
        SimpleUserInfo user = (SimpleUserInfo) session.getAttribute("loginUser");
        if(!commentService.canModify(id, user)) return new ApiResponse(false , "다른 유저의 댓글은 삭제할 수 없습니다!");

        commentService.delete(id);
        return new ApiResponse(true , id+"번 댓글 삭제 성공");
    }

    @GetMapping("/{articleId}")
    public List<Comment> get(@PathVariable int articleId,  @RequestParam int page){
        return commentService.findByArticleId(articleId, page);
    }
}
