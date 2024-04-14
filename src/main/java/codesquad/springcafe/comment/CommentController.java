package codesquad.springcafe.comment;

import codesquad.springcafe.comment.DTO.Comment;
import codesquad.springcafe.comment.DTO.CommentPostReq;
import codesquad.springcafe.comment.repository.CommentRepository;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("comment")
public class CommentController {

    private static final Logger log = getLogger(CommentController.class);
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping("")
    public String postComment(@ModelAttribute CommentPostReq commentPostReq, HttpServletRequest request) {
        SimpleUserInfo author = (SimpleUserInfo) request.getSession().getAttribute("loginUser");
        if (author == null) author = new SimpleUserInfo("guest", "익명");

        log.debug("Comment added at Article Id = " + commentPostReq.articleId());
        commentRepository.add(commentPostReq, author);


        return "redirect:/article/" + commentPostReq.articleId();
    }

    @GetMapping("user/{id}/comments")
    public String comments(@PathVariable("id") String id, Model model) {
        List<Comment> comments = commentRepository.findByUserId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("number", comments.size());

        return "user/comments";
    }
}
