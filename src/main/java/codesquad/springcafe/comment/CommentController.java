package codesquad.springcafe.comment;

import codesquad.springcafe.comment.DTO.CommentPostReq;
import codesquad.springcafe.comment.repository.CommentRepository;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.slf4j.LoggerFactory.getLogger;

@RequestMapping("/comment")
@Controller
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

}
