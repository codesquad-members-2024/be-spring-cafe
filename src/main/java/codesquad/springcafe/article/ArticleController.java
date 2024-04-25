package codesquad.springcafe.article;

import codesquad.springcafe.user.UserDatabase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ArticleController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleDatabase articleDatabase;

    private final UserDatabase userDatabase;

    @Autowired
    public ArticleController(ArticleDatabase articleDatabase, UserDatabase userDatabase) {
        this.articleDatabase = articleDatabase;
        this.userDatabase = userDatabase;
    }

    @PostMapping("/articles")
    public String createQuestion(@ModelAttribute Article article, RedirectAttributes redirectAttributes) {
        if (!userDatabase.isExistUser(article.getWriter())) {
            redirectAttributes.addFlashAttribute("prevTitle", article.getTitle());
            redirectAttributes.addFlashAttribute("prevContent", article.getContent());
            return "redirect:/qna/form";
        }
        articleDatabase.addArticle(article);
        logger.debug("add article : {}", article);
        return "redirect:/";
    }

    @GetMapping("/articles/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model, HttpServletRequest request) {
        Article article = articleDatabase.getArticle(articleId);
        model.addAttribute("article", article);

        // 작성자와 로그인유저가 같은 경우에만 "수정", "삭제" 버튼이 보이게 하기 위함
        HttpSession session = request.getSession();
        session.getAttribute("loginUserId");
        if (article.getWriter().equals(session.getAttribute("loginUserId")))
            model.addAttribute("isWriter", true);
        else
            model.addAttribute("isWriter", false);
        return "article/show";
    }

    @GetMapping("/articles/{articleId}/form")
    public String showEditArticleForm(@PathVariable long articleId, Model model, HttpServletRequest request) {
        Article article = articleDatabase.getArticle(articleId);
        if (!isWriter(article.getWriter(), request)) {
            return "redirect:/error/errorPage";
        }
        model.addAttribute("article", article);
        return "article/editForm";
    }

    @PutMapping("/articles/{articleId}")
    public String editArticle(@ModelAttribute Article editedArticle, @PathVariable long articleId, HttpServletRequest request) {
        editedArticle.setArticleId(articleId);
        Article article = articleDatabase.getArticle(articleId);
        if (!isWriter(article.getWriter(), request)) {
            return "redirect:/error/errorPage";
        }

        articleDatabase.updateArticle(editedArticle);
        return "redirect:/articles/" + articleId;
    }

    @DeleteMapping("/articles/{articleId}")
    public String deleteArticle(@PathVariable long articleId, HttpServletRequest request) {
        Article article = articleDatabase.getArticle(articleId);
        if (!isWriter(article.getWriter(), request)) {
            return "redirect:/error/errorPage";
        }
        articleDatabase.deleteArticle(articleId);
        return "redirect:/";
    }

    private boolean isWriter(String writer, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return writer.equals(session.getAttribute("loginUserId"));
    }
}
