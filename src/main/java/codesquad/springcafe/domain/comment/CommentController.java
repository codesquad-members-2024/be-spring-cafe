package codesquad.springcafe.domain.comment;

import codesquad.springcafe.domain.article.dto.Article;
import codesquad.springcafe.domain.article.service.ArticleService;
import codesquad.springcafe.domain.comment.dto.Comment;
import codesquad.springcafe.domain.comment.service.CommentService;
import codesquad.springcafe.domain.user.dto.UserIdentity;
import codesquad.springcafe.exceptions.NotAuthenticationException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static codesquad.springcafe.constants.Constant.*;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final ArticleService articleService;

    @Autowired
    public CommentController(CommentService commentService, ArticleService articleService) {
        this.commentService = commentService;
        this.articleService = articleService;
    }

    @GetMapping("/create/{writtenArticle}")
    public String showCommentForm(Model model, @PathVariable String writtenArticle) {
        Article article = articleService.getArticle(writtenArticle);

        model.addAttribute("writtenArticle", article);

        return "comment/form";
    }

    @PostMapping("/create")
    public String createComment(@ModelAttribute Comment comment) {
        commentService.addComment(comment);

        return "redirect:/article/show/" + comment.getWrittenArticle();
    }

    @GetMapping("/update/{commentId}")
    public String showCommentUpdatePage(@PathVariable String commentId, Model model, HttpSession session) throws NotAuthenticationException {
        Comment comment = commentService.getCommentById(commentId);
        if (!commentService.userHasPermission((UserIdentity) session.getAttribute(SESSION_LOGIN), comment)) {
            throw new NotAuthenticationException();
        }

        Article article = articleService.getArticle(comment.getWrittenArticle());

        model.addAttribute("comment", comment);
        model.addAttribute("writtenArticle", article);

        return "comment/updateForm";
    }

    @PostMapping("/update")
    public String updateArticle(@ModelAttribute Comment comment, HttpSession session) throws NotAuthenticationException{
        if (!commentService.userHasPermission((UserIdentity) session.getAttribute(SESSION_LOGIN), comment)) {
            throw new NotAuthenticationException();
        }

        commentService.updateComment(comment);

        return "redirect:/article/show/" + comment.getWrittenArticle();
    }
}
