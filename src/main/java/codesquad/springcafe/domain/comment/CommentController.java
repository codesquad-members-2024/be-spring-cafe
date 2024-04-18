package codesquad.springcafe.domain.comment;

import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.comment.DTO.CommentPostReq;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import codesquad.springcafe.exception.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private static final Logger log = getLogger(CommentController.class);
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    // action
    @PostMapping("")
    public String postComment(@ModelAttribute CommentPostReq commentPostReq, HttpServletRequest request, Model model) {
        SimpleUserInfo author = (SimpleUserInfo) request.getSession().getAttribute("loginUser");
        if (author == null) {
            return "error";
        }

        log.debug("Comment added at Article Id = " + commentPostReq.articleId());
        commentService.postComment(commentPostReq, author);

        return "redirect:/article/" + commentPostReq.articleId();
    }

    @PutMapping("/{id}")
    public String modifyComment(@PathVariable int id, @ModelAttribute CommentPostReq commentPostReq, RedirectAttributes ra) {
        commentService.modify(id, commentPostReq);

        ra.addAttribute("id", commentService.getArticleId(id));
        return "redirect:/article/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable int id, RedirectAttributes ra) {
        commentService.delete(id);
        log.info("Comment " + id + "deleted");

        ra.addAttribute("id", commentService.getArticleId(id));
        return "redirect:/article/{id}";
    }


    // show
    @GetMapping("user/{id}/comments")
    public String comments(@PathVariable("id") String id, Model model) {
        List<Comment> comments = commentService.findByUserId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("number", comments.size());

        return "user/comments";
    }


    // form
    @GetMapping("/{id}/form")
    public String getModifyForm(@PathVariable int id, HttpServletRequest request, Model model) {
        SimpleUserInfo loginUser = (SimpleUserInfo) request.getSession().getAttribute("loginUser");

        if (!commentService.canModify(id, loginUser)) {
            throw new AuthorizationException("다른 사람의 댓글을 수정할 수 없습니다!");
        }

        model.addAttribute("commentId", id);
        model.addAttribute("articleId", commentService.getArticleId(id));
        return "comment/form";
    }

    @GetMapping("/{id}/delete")
    public String getDeletePage(@PathVariable int id, HttpServletRequest request, Model model) {
        SimpleUserInfo loginUser = (SimpleUserInfo) request.getSession().getAttribute("loginUser");

        if (!commentService.canModify(id, loginUser)) {
            throw new AuthorizationException("다른 사람의 댓글을 삭제할 수 없습니다!");
        }

        model.addAttribute("commentId", id);
        model.addAttribute("articleId", commentService.getArticleId(id));
        return "comment/delete";
    }
}
