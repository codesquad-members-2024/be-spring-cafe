package codesquad.springcafe.article;

import codesquad.springcafe.article.DTO.ArticlePostReq;
import codesquad.springcafe.article.DTO.ArticleWithComments;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public String showArticle(@PathVariable("id") int id, Model model, HttpServletResponse response) {
        ArticleWithComments article = articleService.getArticle(id);

        // 존재하지 않는 게시글
        if (article == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            model.addAttribute("alert", "요청하신 페이지를 찾을 수 없습니다."); // 이게 작동 안함
            return "error";
        }

        // 정상 흐름
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

        if (articleService.canModify(id, loginUser)) {
            model.addAttribute("articleId", id);
            return "article/update_form";
        }

        model.addAttribute("alert", "다른 사람의 게시글을 수정할 수 없습니다!");
        return "error";
    }

    @GetMapping("/{id}/delete")
    public String getDeletePage(@PathVariable int id, HttpServletRequest request, Model model) {
        SimpleUserInfo loginUser = (SimpleUserInfo) request.getSession().getAttribute("loginUser");

        if (articleService.canModify(id, loginUser)) {
            model.addAttribute("articleId", id);
            System.out.println(id);
            return "article/delete";
        }

        model.addAttribute("alert", "다른 사람의 게시글을 삭제할 수 없습니다!");
        return "error";
    }
}
