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
    public String create(@PathVariable("id") Long id, @RequestParam(value = "content") String content, HttpSession session) {
        Article article = this.articleService.findById(id);
        User user = (User) session.getAttribute("user");

        replyService.create(article, content, user.getUserId());
        return "redirect:/qna/show/" + id;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        Reply replyToDelete = replyService.findByReplyId(id);
        User user = (User) session.getAttribute("user");

        if (!replyToDelete.matchesWriter(user.getUserId())) {
            throw new WrongWriterException("권한이 없습니다.");
        }

        Long articleId = replyToDelete.getArticleId();
        replyService.delete(id);

        return "redirect:/qna/show/" + articleId;
    }
}
