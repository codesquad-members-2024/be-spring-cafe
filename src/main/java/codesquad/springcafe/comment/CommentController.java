package codesquad.springcafe.comment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    private final CommentDatabase commentDatabase;
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    public CommentController(CommentDatabase commentDatabase) {
        this.commentDatabase = commentDatabase;
    }

    @PostMapping("/articles/{articleId}/comments")
    public String createComment(@ModelAttribute CommentCreateDTO commentCreateDTO, @PathVariable Long articleId) {
        commentDatabase.createComment(commentCreateDTO);
        return "redirect:/articles/" + articleId;
    }

    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public String deleteComment(@PathVariable Long articleId, @PathVariable Long commentId, HttpServletRequest request) {
        if (!isCommentWriter(commentDatabase.getCommentWriter(commentId), request)) {
            return "redirect:/error/errorPage";
        }
        logger.debug("댓글 삭제, commentId : {}", commentId);
        commentDatabase.deleteComment(commentId);
        return "redirect:/articles/" + articleId;
    }

    @PutMapping("/articles/{articleId}/comments/{commentId}")
    public String updateComment(@ModelAttribute CommentEditDTO commentEditDTO,
                                @PathVariable Long articleId,
                                @PathVariable Long commentId,
                                HttpServletRequest request) {
        if (!isCommentWriter(commentDatabase.getCommentWriter(commentId), request)) {
            return "redirect:/error/errorPage";
        }
        commentDatabase.editComment(commentEditDTO);
        return "redirect:/articles/" + articleId;
    }

    // 버튼 노출 여부와 상관없이 직접 경로접근을 막기 위한 확인과정
    private boolean isCommentWriter(String writer, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return writer.equals(session.getAttribute("loginUserId"));
    }
}
