package codesquad.springcafe.web.controller;

import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.domain.user.User;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.CommentService;
import codesquad.springcafe.web.dto.ArticleCreateDto;
import codesquad.springcafe.web.dto.ArticleUpdateDto;
import codesquad.springcafe.web.validation.ArticleCreateValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleCreateValidator articleCreateValidator;
    private final CommentService commentService;

    public ArticleController(ArticleService articleService, ArticleCreateValidator articleCreateValidator, CommentService commentService) {
        this.articleService = articleService;
        this.articleCreateValidator = articleCreateValidator;
        this.commentService = commentService;
    }

    @InitBinder("create")
    public void initTargetCreate(WebDataBinder dataBinder) {
        dataBinder.addValidators(articleCreateValidator);
    }

    @GetMapping("/articles/create")
    public String quest(Model model) {
        model.addAttribute("create", new ArticleCreateDto());
        return "qna/form";
    }

    @PostMapping("/articles/create")
    public String quest(
            @SessionAttribute(name = "loginUser", required = false) User loginUser,
            @Validated @ModelAttribute("create") ArticleCreateDto articleCreateDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "qna/form";
        }
        articleService.saveArticle(loginUser, articleCreateDto);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String articleDetails(@PathVariable Long id, Model model) {
        model.addAttribute("nlString", System.lineSeparator());
        model.addAttribute("article", articleService.findById(id));
        model.addAttribute("comments", commentService.getCommentsByArticleId(id));
        return "qna/show";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("articles", articleService.getArticles());
        return "index";
    }

    @GetMapping("/articles/{id}/update")
    public String updateArticle(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.findById(id));
        return "qna/updateForm";
    }

    @PutMapping("/articles/{id}/update")
    public String updateArticle(
            @SessionAttribute(name = "loginUser", required = false) User loginUser,
            @PathVariable Long id,
            @ModelAttribute ArticleUpdateDto articleUpdateDto) {
        // 로그인된 사용자가 글의 주인인지 검증하는 로직 추가 예정
        articleService.updateArticle(id, articleUpdateDto);
        return "redirect:/";
    }

    @DeleteMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return "redirect:/";
    }

    @PostMapping("/articles/{articleId}/comments/create")
    public String writeComment(
            @SessionAttribute(name = "loginUser", required = false) User loginUser,
            @PathVariable Long articleId,
            @RequestParam String content) {
        commentService.saveComment(new Comment(loginUser.getId(), articleId, loginUser.getUserId(), content, LocalDateTime.now()));
        return "redirect:/articles/" + articleId;
    }

    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public String deleteComment(
            @PathVariable Long articleId,
            @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/articles/" + articleId;
    }
}