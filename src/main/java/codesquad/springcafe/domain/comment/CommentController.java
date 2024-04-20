package codesquad.springcafe.domain.comment;

import codesquad.springcafe.domain.article.dto.Article;
import codesquad.springcafe.domain.article.service.ArticleService;
import codesquad.springcafe.domain.comment.dto.Comment;
import codesquad.springcafe.domain.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
}
