package springcafe.article.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcafe.article.model.Article;
import springcafe.article.dto.ArticleForm;
import springcafe.article.service.ArticleService;
import springcafe.reply.model.Reply;
import springcafe.reply.service.ReplyService;
import springcafe.user.exception.WrongWriterException;
import springcafe.user.model.User;

import java.util.List;


@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ReplyService replyService;

    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @GetMapping("/")
    public String showArticleList(Model model) {
        List<Article> articleList = articleService.findAllArticles();
        model.addAttribute("articleList", articleList);

        return "index";
    }

    @GetMapping("qna/show/{articleId}")
    public String displayArticleDetails(@PathVariable("articleId") Long articleId, Model model, HttpSession session) {

        Article article = articleService.findByArticleId(articleId);
        User user = (User) session.getAttribute("user");
        List<Reply> replyList = replyService.findReplyByArticleId(articleId);

        model.addAttribute("lineSeparator", System.lineSeparator());
        model.addAttribute("article", article);
        model.addAttribute("user", user);
        model.addAttribute("replyList", replyList);
        return "qna/show";
    }

    @GetMapping("qna/questions")
    public String showQuestionCreationForm(@ModelAttribute("article") ArticleForm article) {

        return "qna/form";
    }

    @PostMapping("qna/questions")
    public String processQuestionCreation(@Valid ArticleForm article, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {

            return "qna/form";

        }

        User user = (User) session.getAttribute("user");
        this.articleService.saveArticle(user.getUserId(), article.getTitle(), article.getContents(), user.getId());
        return "redirect:/";
    }

    @GetMapping("qna/update/{id}")
    public String showUpdateForm(@PathVariable Long id, @ModelAttribute("article") Article article, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Article articleToUpdate = articleService.findByArticleId(id);
        if (!user.matchUserId(articleToUpdate.getWriter())) {
            throw new WrongWriterException("다른 사람의 글을 수정할 수 없습니다.");
        }

        return "qna/update";
    }

    @PutMapping("qna/update/{id}")
    public String processArticleUpdate(@PathVariable Long id, ArticleForm article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "qna/update";
        }

        articleService.update(id, article.getTitle(), article.getContents());

        return "redirect:/";
    }

    @DeleteMapping("/qna/delete/{id}")
    public String deleteArticle(@PathVariable Long id, HttpSession session) {
        Article articleToDelete = articleService.findByArticleId(id);
        User user = (User) session.getAttribute("user");


        List<Reply> replyList = replyService.findReplyByArticleId(id);

        if (!articleToDelete.getWriter().equals(user.getUserId())
                || !articleService.checkIfPossibleToDelete(replyList, articleToDelete.getWriter())) {
            throw new WrongWriterException("삭제 권한이 없습니다.");
        }


        articleService.deleteArticle(id);

        return "redirect:/";
    }
}
