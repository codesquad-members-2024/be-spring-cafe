package codesquad.springcafe.controller;

import codesquad.springcafe.dto.ArticleUpdateDto;
import codesquad.springcafe.dto.ArticleWriteDto;
import codesquad.springcafe.exception.service.DuplicateUserIdException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.model.UpdatedArticle;
import codesquad.springcafe.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/write")
    public String showWritePage(@ModelAttribute("articleWriteDto") ArticleWriteDto articleWriteDto) {
        return "article/form";
    }

    @PostMapping("/write")
    public String processWriteForm(@Valid @ModelAttribute ArticleWriteDto articleWriteDto, BindingResult bindingResult,
                                   HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "article/form";
        }

        try {
            SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser"); // 로그인해야지 접근할 수 있으므로
            String userId = sessionUser.getUserId();
            Article article = articleWriteDto.createArticle(userId);
            articleService.addArticle(article);
            sessionUser.addArticleId(article.getId()); // 세션 유저에 작성한 게시글 Id 추가
            return "redirect:/";
        } catch (DuplicateUserIdException e) {
            bindingResult.reject("errorAid");
            return "article/form";
        }
    }

    @GetMapping("/show/{articleId}")
    public String showDetailPage(@PathVariable long articleId, Model model, HttpSession httpSession) {
        articleService.increaseViewCount(articleId);
        Article article = articleService.findArticleById(articleId);

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        boolean isWriter = sessionUser.getArticleIds().contains(articleId);

        model.addAttribute("article", article);
        model.addAttribute("isWriter", isWriter);
        return "article/show";
    }

    @GetMapping("/update/{articleId}")
    public String showUpdatePage(@PathVariable Long articleId,
                                 @ModelAttribute("articleUpdateDto") ArticleUpdateDto articleUpdateDto) {
        Article article = articleService.findArticleById(articleId);
        articleUpdateDto.setTitle(article.getTitle());
        articleUpdateDto.setContent(article.getContent());
        return "article/update";
    }

    @PutMapping("/update/{articleId}")
    public String processUpdateForm(@PathVariable long articleId, HttpSession httpSession,
                                    @Valid @ModelAttribute ArticleUpdateDto articleUpdateDto,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/update";
        }

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        UpdatedArticle updatedArticle = articleUpdateDto.createUpdatedArticle(sessionUser.getUserId());
        articleService.updateArticle(articleId, updatedArticle);
        return "redirect:/";
    }

    @DeleteMapping("/delete/{articleId}")
    public String processDelete(@PathVariable long articleId) {
        articleService.deleteArticle(articleId);
        return "redirect:/";
    }

    @GetMapping("/invalid-modify")
    public String showInvalidModifyPage(Model model) {
        model.addAttribute("errorMsg", "다른 사람의 게시글은 수정할 수 없습니다.");
        return "error/form";
    }
}
