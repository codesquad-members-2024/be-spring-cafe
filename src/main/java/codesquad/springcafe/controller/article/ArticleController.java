package codesquad.springcafe.controller.article;

import codesquad.springcafe.controller.SessionConst;
import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.service.article.ArticleManager;
import java.time.LocalDateTime;
import java.util.Optional;
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

    @Autowired
    public ArticleController(ArticleManager articleManager) {
        this.articleManager = articleManager;
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
    public String detail(@PathVariable("articleId") long id, Model model) {
        Optional<Article> optionalArticle = articleManager.findArticle(id);
        if (optionalArticle.isEmpty()) {
            return "error/404";
        }

        /* 줄바꿈 문자 추가 */
        String lineSeparator = System.lineSeparator();
        model.addAttribute("lineSeparator", lineSeparator);

        /* 모델 속성 추가 */
        model.addAttribute("article", optionalArticle.get());

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
        /* 게시물 존재 검증 */
        articleManager.validateExists(articleId);

        /* 작성자 검증 */
        Article findArticle = articleManager.findArticle(articleId).get();
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
        /* 게시물 존재 검증 */
        articleManager.validateExists(articleId);

        /* 작성자 검증 */
        Optional<Article> optionalArticle = articleManager.findArticle(articleId);
        articleManager.validateAuthor(loginId, optionalArticle.get().getCreatedBy());

        return "qna/deleteConfirm";
    }

    @DeleteMapping("/{articleId}")
    public String unpublish(@SessionAttribute(name = SessionConst.SESSION_ID) String loginId,
                            @PathVariable("articleId") long id) {
        articleManager.unpublish(loginId, id);

        return "redirect:/";
    }
}
