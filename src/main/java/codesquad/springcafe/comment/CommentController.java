package codesquad.springcafe.comment;

import codesquad.springcafe.comment.dto.CommentCreationRequestDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/qna/{articleId}/comment")
    public String saveComment(@PathVariable int articleId, HttpSession session,
                              CommentCreationRequestDto dto) {
        String loginUserId = (String) session.getAttribute("sessionUserId");
        if (loginUserId == null) {
            return "redirect:/form/login";
        }

        service.save(articleId, dto);
        return "redirect:/article/" + articleId;
    }
}
