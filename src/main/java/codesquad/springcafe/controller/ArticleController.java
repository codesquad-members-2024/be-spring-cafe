package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.ArticleDto;
import codesquad.springcafe.error.exception.AccessDeniedException;
import codesquad.springcafe.service.ArticleService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/create")
    public String createArticleForm() {
        return "article/create";
    }

    @PostMapping("/create")
    public String createArticle(@ModelAttribute ArticleDto articleDto, HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        String userId = loginUser.getUserId();

        Article article = new Article(userId, articleDto);
        articleService.createArticle(article);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showArticle(@PathVariable long id, Model model, HttpSession session) {
        articleService.updateViews(id); // 조회수 먼저 업데이트

        Article article = articleService.findById(id);
        model.addAttribute("article", article);

        User loginUser = (User) session.getAttribute("user");
        if (loginUser != null && article.isWriter(loginUser.getUserId())) {
            model.addAttribute("writer", true);
        }

        return "article/page";
    }

    @GetMapping("/{id}/update")
    public String updateArticleForm(@PathVariable long id, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("user");

        Article article = articleService.findById(id);
        if (!article.isWriter(loginUser.getUserId())) {
            throw new AccessDeniedException("게시글 수정에 대한 권한이 없습니다.");
        }

        model.addAttribute("article", article);

        return "article/update";
    }

    @PutMapping("/{id}/update")
    public String updateArticle(@PathVariable long id, @ModelAttribute ArticleDto articleDto) {
        articleService.updateArticle(id, articleDto);

        return "redirect:/articles/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable long id, HttpSession session) {
        User loginUser = (User) session.getAttribute("user");

        Article article = articleService.findById(id);
        if (!article.isWriter(loginUser.getUserId())) {
            throw new AccessDeniedException("게시글 삭제에 대한 권한이 없습니다.");
        }

        articleService.deleteArticle(id);

        return "redirect:/";
    }
}