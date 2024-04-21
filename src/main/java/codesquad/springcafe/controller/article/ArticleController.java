package codesquad.springcafe.controller.article;

import codesquad.springcafe.controller.SessionConst;
import codesquad.springcafe.controller.comment.CommentForm;
import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.service.article.ArticleManager;
import codesquad.springcafe.service.comment.CommentManager;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/questions")
public class ArticleController {
    private final ArticleManager articleManager;
    private final CommentManager commentManager;

    @Autowired
    public ArticleController(ArticleManager articleManager, CommentManager commentManager) {
        this.articleManager = articleManager;
        this.commentManager = commentManager;
    }

    @GetMapping
    public String publish(@SessionAttribute(name = SessionConst.SESSION_ID) String loginId,
                          @ModelAttribute("article") ArticleForm form) {
        form.setCreatedBy(loginId);

        return "qna/form";
    }

    @PostMapping
    public String publish(@Validated @ModelAttribute("article") ArticleForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "qna/form";
        }

        Article article = convertToArticle(form);

        articleManager.publish(article);

        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String detail(@PathVariable("articleId") long articleId, Model model) {
        /* 줄바꿈 문자 추가 */
        String lineSeparator = System.lineSeparator();
        model.addAttribute("lineSeparator", lineSeparator);

        /* 게시글 추가 */
        Article findArticle = articleManager.findArticle(articleId);
        model.addAttribute("article", findArticle);

        /* 댓글 리스트 추가 */
        List<Comment> comments = commentManager.findAllComment(articleId);
        model.addAttribute("comments", comments);

        /* 댓글 폼 추가 */
        model.addAttribute("commentForm", new CommentForm());

        return "qna/detail";
    }

    private Article convertToArticle(ArticleForm form) {
        Article article = new Article();

        article.setTitle(form.getTitle());
        article.setContents(form.getContents());
        article.setCreatedBy(form.getCreatedBy());
        article.setCreatedAt(LocalDateTime.now());

        return article;
    }

    @GetMapping("/{articleId}/edit")
    public String editForm(@SessionAttribute(name = SessionConst.SESSION_ID) String loginId,
                           @PathVariable("articleId") long articleId, @ModelAttribute("form") UpdateArticle form) {
        Article findArticle = articleManager.findArticle(articleId);

        /* 작성자 검증 */
        articleManager.validateAuthor(loginId, findArticle.getCreatedBy());

        /* 정상 로직 */
        fillForm(form, findArticle);

        return "qna/updateForm";
    }

    private void fillForm(UpdateArticle form, Article findArticle) {
        form.setId(findArticle.getId());
        form.setCreatedBy(findArticle.getCreatedBy());
        form.setTitle(findArticle.getTitle());
        form.setContents(findArticle.getContents());
    }

    @PutMapping("/{articleId}")
    public String edit(@SessionAttribute(name = SessionConst.SESSION_ID) String loginId,
                       @PathVariable("articleId") long articleId, @Validated @ModelAttribute("form") UpdateArticle form,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "qna/updateForm";
        }

        articleManager.editArticle(loginId, form);

        return "redirect:/questions/{articleId}";
    }

    @GetMapping("/{articleId}/delete")
    public String confirmUnpublish(@SessionAttribute(name = SessionConst.SESSION_ID) String loginId,
                                  @PathVariable("articleId") long articleId) {
        Article findArticle = articleManager.findArticle(articleId);

        /* 작성자 검증 */
        articleManager.validateAuthor(loginId, findArticle.getCreatedBy());

        return "qna/deleteConfirm";
    }

    @DeleteMapping("/{articleId}")
    public String unpublish(@SessionAttribute(name = SessionConst.SESSION_ID) String loginId,
                            @PathVariable("articleId") long articleId) {
        Article findArticle = articleManager.findArticle(articleId);

        /* 작성자 검증 */
        articleManager.validateAuthor(loginId, findArticle.getCreatedBy());

        /* 정상 흐름 */
        articleManager.unpublish(articleId);

        return "redirect:/";
    }
}
