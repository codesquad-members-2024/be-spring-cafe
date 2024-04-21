package codesquad.springcafe.controller.comment;

import codesquad.springcafe.controller.SessionConst;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.service.comment.CommentManager;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/questions/{articleId}/comments")
public class CommentController {
    private final CommentManager commentManager;

    @Autowired
    public CommentController(CommentManager commentManager) {
        this.commentManager = commentManager;
    }

    @PostMapping
    public String publishComment(@SessionAttribute(name = SessionConst.SESSION_ID) String loginId,
                                 @PathVariable("articleId") long articleId,
                                 @ModelAttribute("commentForm") CommentForm form) {
        Comment comment = createComment(loginId, articleId, form);

        commentManager.publish(comment);

        return "redirect:/questions/{articleId}";
    }

    @GetMapping("/{commentId}/edit")
    public String editForm(@SessionAttribute(name = SessionConst.SESSION_ID) String loginId,
                           @PathVariable("commentId") long commentId, @PathVariable("articleId") long articleId,
                           @ModelAttribute("form") CommentUpdateForm form) {
        Comment comment = commentManager.findComment(commentId);

        /* 작성자 검증 */
        commentManager.validateAuthor(loginId, comment.getCreatedBy());

        /* 정상 로직 */
        fillForm(form, comment);

        return "qna/commentUpdateForm";
    }

    @PutMapping("/{commentId}")
    public String edit(@SessionAttribute(name = SessionConst.SESSION_ID) String loginId,
                       @PathVariable("commentId") long commentId, @PathVariable("articleId") long articleId,
                       @Validated @ModelAttribute("form") CommentUpdateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "qna/commentUpdateForm";
        }

        commentManager.editComment(loginId, form);

        return "redirect:/questions/{articleId}";
    }

    @DeleteMapping("/{commentId}")
    public String delete(@SessionAttribute(name = SessionConst.SESSION_ID) String loginId,
                         @PathVariable("commentId") long commentId, @PathVariable("articleId") long articleId) {
        Comment comment = commentManager.findComment(commentId);

        /* 작성자 검증 */
        commentManager.validateAuthor(loginId, comment.getCreatedBy());

        /* 정상 로직 */
        commentManager.unpublish(commentId);

        return "redirect:/questions/{articleId}";
    }

    private Comment createComment(String loginId, long articleId, CommentForm form) {
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setContent(form.getContent());
        comment.setCreatedBy(loginId);
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }

    private void fillForm(CommentUpdateForm form, Comment comment) {
        form.setId(comment.getId());
        form.setArticleId(comment.getArticleId());
        form.setAuthor(comment.getCreatedBy());
        form.setContent(comment.getContent());
    }
}
