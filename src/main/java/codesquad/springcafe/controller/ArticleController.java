package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.ArticleForm;
import codesquad.springcafe.dto.EditArticleForm;
import codesquad.springcafe.exception.InvalidAccessException;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.validator.ArticleValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleValidator articleValidator;

    @Autowired
    public ArticleController(ArticleService articleService, ArticleValidator articleValidator) {
        this.articleService = articleService;
        this.articleValidator = articleValidator;
    }

    @GetMapping("/article")
    public String showForm() {
        return "article/form";
    }

    /**
     * 세션 값을 통해 로그인한 사용자를 확인한 후 게시글을 등록한다.
     * @param articleForm
     * @param session
     * @return
     */
    @PostMapping("/article")
    public String register(ArticleForm articleForm, HttpSession session) {
        User user = (User) session.getAttribute("sessionUser");
        articleService.register(articleForm, user.getUserId());
        return "redirect:/";
    }

    /**
     * 게시글의 상세 정보 페이지를 보여준다. 로그인한 사용자 본인의 게시글이 아닌 경우 수정, 삭제 버튼이 비활성화된다.
     *
     * @param model
     * @param articleId
     * @param session
     * @return
     */
    @GetMapping("/article/{articleId}")
    public String showArticle(Model model, @PathVariable("articleId") String articleId, HttpSession session) {
        try {
            User writer = (User) session.getAttribute("sessionUser");
            articleValidator.validWriter(writer, articleId);
            model.addAttribute("validWriter", true);
        } catch (InvalidAccessException e) {
            model.addAttribute("validWriter", false);
        }
        model.addAttribute("article", articleService.getArticleDetail(articleId));
        return "article/show";
    }

    /**
     * 게시글의 수정 form을 보여준다. 로그인한 사용자 본인의 게시글이 아닌 경우 403 예외가 발생한다.
     *
     * @param articleId
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/article/edit/{articleId}")
    public String showEditForm(@PathVariable("articleId") String articleId, Model model, HttpSession session) {
        User writer = (User) session.getAttribute("sessionUser");
        articleValidator.validWriter(writer, articleId);
        Article target = articleService.getArticleDetail(articleId);
        model.addAttribute("targetArticle", target);
        return "article/editForm";
    }

    /**
     * 게시글을 수정한다. 로그인한 사용자 본인의 게시글이 아닌 경우 403 예외가 발생한다.
     *
     * @param articleId
     * @param editArticleForm
     * @param session
     * @return
     */
    @PutMapping("/article/edit/{articleId}")
    public String editArticle(@PathVariable("articleId") String articleId, EditArticleForm editArticleForm,
                              HttpSession session) {
        User writer = (User) session.getAttribute("sessionUser");
        articleValidator.validWriter(writer, articleId);
        articleService.update(articleId, editArticleForm);
        return "redirect:/";
    }

    /**
     * 게시글을 삭제한다. 로그인한 사용자 본인의 게시글이 아닌 경우 403 예외가 발생한다.
     *
     * @param articleId
     * @param session
     * @return
     */
    @DeleteMapping("/article/delete/{articleId}")
    public String deleteArticle(@PathVariable("articleId") String articleId, HttpSession session) {
        User writer = (User) session.getAttribute("sessionUser");
        articleValidator.validWriter(writer, articleId);
        articleService.delete(articleId);
        return "redirect:/";
    }
}