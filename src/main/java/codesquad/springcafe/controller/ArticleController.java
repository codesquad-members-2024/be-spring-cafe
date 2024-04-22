package codesquad.springcafe.controller;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.exception.UnauthorizedAccessException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/question")
public class ArticleController {

    public static final String LOGIN_USER_ID = "loginUserId";
    public static final String ARTICLE_ID = "articleId";

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String getQuestionForm() {
        return "qna/form";
    }

    @PostMapping
    public String writeQuestion(@ModelAttribute ArticleRequestDto articleRequestDto, HttpSession session) {
        String userId = (String) session.getAttribute(LOGIN_USER_ID);
        Article article = new Article(userId, articleRequestDto);
        articleService.createArticle(article);
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String getArticle(@PathVariable Long articleId, Model model) {
        Article article = articleService.findById(articleId);
        log.debug("getArticle : {}", article);
        model.addAttribute("article", article);
        return "qna/show";
    }

    @GetMapping("/update/{articleId}")
    public String getArticleUpdateForm(@PathVariable Long articleId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute(LOGIN_USER_ID);
        if (articleService.checkArticleWriter(articleId, userId)) {
            log.debug("게시물 수정 성공 {}", userId);
            model.addAttribute(ARTICLE_ID, articleId);
            return "qna/update_form";
        }
        log.debug("게시물 수정 실패 {}", userId);
        throw new UnauthorizedAccessException("본인의 게시글만 수정할 수 있습니다.");
    }

    @PutMapping("{articleId}")
    public String updateArticle(@PathVariable Long articleId, @ModelAttribute ArticleRequestDto articleRequestDto) {
        articleService.update(articleId, articleRequestDto);
        return "redirect:/question/" + articleId;
    }

    @DeleteMapping("{articleId}")
    public String deleteArticle(@PathVariable Long articleId, HttpSession session){
        String userId = (String) session.getAttribute(LOGIN_USER_ID);
        if (articleService.checkArticleWriter(articleId, userId)) {
            log.debug("게시물 삭제 성공 {}", userId);
            articleService.delete(articleId);
            return "redirect:/";
        }
        log.debug("게시물 삭제 실패 {}", userId);
        throw new UnauthorizedAccessException("본인의 게시글만 삭제할 수 있습니다.");
    }
}
