package codesquad.springcafe.article;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (!articleService.findById(articleId).getUserId().equals(userId)) {
            return "redirect:/";
        }
        model.addAttribute("article", articleService.findById(articleId));
        return "article/update_form";
    }

    //게시글 수정
    @PatchMapping("/{articleId}/update_form")
    public String updateArticle(@PathVariable Long articleId, ArticleUpdateDto articleUpdateDto) {
        articleService.update(articleUpdateDto, articleId);
        return "redirect:/";
    }
}
