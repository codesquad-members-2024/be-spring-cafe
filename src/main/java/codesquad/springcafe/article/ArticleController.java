package codesquad.springcafe.article;

import codesquad.springcafe.article.dto.ArticleCreateDto;
import codesquad.springcafe.article.dto.ArticleUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/form")
    public String articleForm() {
        return "article/form";
    }

    @PostMapping("/form")
    public String createArticle(ArticleCreateDto articleCreateDto, HttpServletRequest request) {
        articleService.save(articleCreateDto.toEntity());
        log.info(request.getSession().getAttribute("nickname") + " created article");
        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable Long articleId, Model model) {
        model.addAttribute("article", articleService.findById(articleId));
        return "article/show";
    }

    // 게시글 수정 페이지 form
    @GetMapping("/{articleId}/update_form")
    public String updateArticleForm(@PathVariable Long articleId, Model model,
        HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (!articleService.isAuthenticated(userId, articleId)) {
            return "redirect:/";
        }
        model.addAttribute("article", articleService.findById(articleId));
        return "article/update_form";
    }

    //게시글 수정
    @PatchMapping("/{articleId}/update_form")
    public String updateArticle(@PathVariable Long articleId, ArticleUpdateDto articleUpdateDto,
        HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (!articleService.isAuthenticated(userId, articleId)) {
            return "redirect:/";
        }
        articleService.update(articleUpdateDto, articleId);
        return "redirect:/";
    }

    //게시글 삭제
    @DeleteMapping("/{articleId}/delete")
    public String deleteArticle(@PathVariable Long articleId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (!articleService.isAuthenticated(userId, articleId)) {
            return "redirect:/";
        }
        articleService.delete(articleId);
        log.info(
            request.getSession().getAttribute("nickname") + " deleted article number " + articleId);
        return "redirect:/";
    }

}
