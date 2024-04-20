package codesquad.springcafe.domain.article;

import codesquad.springcafe.domain.article.DTO.ArticlePostReq;
import codesquad.springcafe.domain.article.DTO.ArticleWithComments;
import codesquad.springcafe.exception.AuthorizationException;
import codesquad.springcafe.exception.NotFoundException;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private static final Logger log = getLogger(ArticleController.class);

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    // action
    @PostMapping("")
    public String postArticle(@ModelAttribute ArticlePostReq articlePostReq, HttpServletRequest request) {
        SimpleUserInfo author = (SimpleUserInfo) request.getSession().getAttribute("loginUser");
        articleService.postArticle(articlePostReq, author);

        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String modifyArticle(@PathVariable int id, @ModelAttribute ArticlePostReq articlePostReq, RedirectAttributes ra) {
        articleService.modify(id, articlePostReq);

        ra.addAttribute("id", id);
        return "redirect:/article/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable int id) {
        articleService.delete(id);
        log.info("Article " + id + "deleted");

        return "redirect:/";
    }

    // view
    @GetMapping("/{id}")
    public String showArticle(@PathVariable("id") int id, Model model) throws NotFoundException{
        ArticleWithComments article = articleService.getArticle(id);

        model.addAttribute("article", article.article());
        model.addAttribute("comments", article.comments());
        model.addAttribute("numberOfComments", article.comments().size());
        return "article/show";
    }


    // form
    @GetMapping("/form")
    public String articleForm() {
        return "article/form";
    }

    @GetMapping("user/{id}/articles")
    public String articles(@PathVariable("id") String id, Model model) {
        List<Article> articles = articleService.findByUserId(id);
        model.addAttribute("articles", articles);
        model.addAttribute("points", articles.stream().mapToInt(Article::getPoint).sum());

        return "user/articles";
    }

    @GetMapping("/{id}/form")
    public String getModifyForm(@PathVariable int id, HttpServletRequest request, Model model) {
        SimpleUserInfo loginUser = (SimpleUserInfo) request.getSession().getAttribute("loginUser");

        if (!articleService.canModify(id, loginUser)) {
            throw new AuthorizationException("다른 사람의 게시글을 수정할 수 없습니다!");
        }

        model.addAttribute("articleId", id);
        return "article/update_form";
    }

    @GetMapping("/{id}/delete")
    public String getDeletePage(@PathVariable int id, HttpServletRequest request, Model model) {
        SimpleUserInfo loginUser = (SimpleUserInfo) request.getSession().getAttribute("loginUser");

        if (!articleService.canDelete(id, loginUser)) {
            throw new AuthorizationException("해당 게시글을 삭제할 수 없습니다!");
        }

        model.addAttribute("articleId", id);
        return "article/delete";
    }
}
