package codesquad.springcafe.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private final CommentDatabase commentDatabase;
    @Autowired
    public CommentController(CommentDatabase commentDatabase) {
        this.commentDatabase = commentDatabase;
    }

    @PostMapping("/articles/{articleId}/comments")
    public String createComment(@ModelAttribute CommentCreateDTO commentCreateDTO, @PathVariable Long articleId) {
        commentDatabase.createComment(commentCreateDTO);
        return "redirect:/articles/" + articleId;
    }
}
