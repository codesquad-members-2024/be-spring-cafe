package codesquad.springcafe.article;

import codesquad.springcafe.article.dto.ArticleCreateDto;
import codesquad.springcafe.article.dto.ArticleUpdateDto;
import codesquad.springcafe.reply.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ReplyService replyService;

    @Autowired
    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
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
        model.addAttribute("replies", replyService.findByArticleId(articleId));
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
    @DeleteMapping("/{replyId}/delete")
    public String deleteArticle(@PathVariable Long replyId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (!articleService.isAuthenticated(userId, replyId)) {
            return "redirect:/";
        }
        articleService.delete(replyId);
        log.info(
            request.getSession().getAttribute("nickname") + " deleted article number " + replyId);
        return "redirect:/";
    }

}
