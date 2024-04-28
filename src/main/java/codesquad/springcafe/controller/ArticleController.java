package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.ArticleDto;
import codesquad.springcafe.error.exception.AccessDeniedException;
import codesquad.springcafe.error.exception.UserNotFoundException;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final ReplyService replyService;

    @Autowired
    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @GetMapping("/create")
    public String createArticleForm() {
        return "article/create";
    }

    @PostMapping("/create")
    public String createArticle(@ModelAttribute ArticleDto articleDto, HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null) {
            throw new UserNotFoundException("로그인 사용자가 존재하지 않습니다.");
        }

        Article article = new Article(loginUser.getUserId(), articleDto);
        articleService.createArticle(article);

        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model, HttpSession session) {
        articleService.updateViews(articleId); // 조회수 먼저 업데이트

        Article article = articleService.findByArticleId(articleId);
        List<Reply> replies = replyService.findRepliesByArticleId(articleId);

        model.addAttribute("article", article);
        model.addAttribute("replies", replies);

        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null) {
            throw new UserNotFoundException("로그인 사용자가 존재하지 않습니다.");
        }

        if (article.isWriter(loginUser.getUserId())) {
            model.addAttribute("isWriter", true);
        }
        model.addAttribute("user", loginUser);

        return "article/page";
    }

    @GetMapping("/{articleId}/update")
    public String updateArticleForm(@PathVariable long articleId, Model model, HttpSession session) {
        validateAuth(session, articleId);

        Article article = articleService.findByArticleId(articleId);
        model.addAttribute("article", article);

        return "article/update";
    }

    @PutMapping("/{articleId}/update")
    public String updateArticle(@PathVariable long articleId, @ModelAttribute ArticleDto articleDto,
                                HttpSession session) {
        validateAuth(session, articleId);
        articleService.updateArticle(articleId, articleDto);

        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/{articleId}")
    public String deleteArticle(@PathVariable long articleId, HttpSession session) {
        validateAuth(session, articleId);
        articleService.deleteArticle(articleId);

        return "redirect:/";
    }

    private void validateAuth(HttpSession session, long articleId) {
        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null) {
            throw new UserNotFoundException("로그인 사용자가 존재하지 않습니다.");
        }

        Article article = articleService.findByArticleId(articleId);

        if (!article.isWriter(loginUser.getUserId())) {
            throw new AccessDeniedException("게시글 접근 권한이 없습니다.");
        }
    }
}