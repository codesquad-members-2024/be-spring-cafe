package springcafe.reply.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springcafe.article.model.Article;
import springcafe.article.service.ArticleService;
import springcafe.reply.model.Reply;
import springcafe.reply.service.ReplyService;
import springcafe.user.exception.WrongWriterException;
import springcafe.user.model.User;

@RequestMapping("/reply")
@Controller
public class ReplyController {

    private final ArticleService articleService;
    private final ReplyService replyService;


    public ReplyController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @PostMapping("/create/{id}")
    public String processReplyCreation(@PathVariable("id") Long id, @RequestParam(value = "content") String content, HttpSession session) {
        Article article = this.articleService.findByArticleId(id);
        User user = (User) session.getAttribute("user");

        replyService.saveReply(article, content, user.getUserId());
        return "redirect:/qna/show/" + id;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteReply(@PathVariable("id") Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Long articleId = replyService.deleteReply(id, user.getUserId());
        return "redirect:/qna/show/" + articleId;
    }
}
