package codesquad.springcafe.controller;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.form.article.ArticleWriteForm;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.User;
import codesquad.springcafe.util.LoginUserProvider;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleDatabase articleDatabase;

    public ArticleController(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    /**
     * 사용자에게 아티클 폼을 보여줍니다.
     */
    @GetMapping("/add")
    public String articleForm(Model model) {
        ArticleWriteForm articleWriteForm = new ArticleWriteForm("", "");
        model.addAttribute("articleWriteForm", articleWriteForm);
        return "article/form";
    }

    /**
     * 사용자가 작성한 아티클을 생성하고 데이터베이스에 저장합니다.
     */
    @PostMapping("/add")
    public String addForm(@Validated @ModelAttribute ArticleWriteForm articleWriteForm, BindingResult bindingResult,
                          HttpSession session) {
        if (bindingResult.hasErrors()) {
            logger.error("errors ={}", bindingResult);
            return "article/form";
        }
        User loginUser = LoginUserProvider.provide(session);
        Article article = new Article(loginUser.getNickname(), articleWriteForm.getTitle(),
                articleWriteForm.getContent());
        articleDatabase.add(article);
        logger.info("새로운 게시물이 추가되었습니다. {}", article);
        return "redirect:/";
    }

    /**
     * 사용자가 요청한 id의 아티클을 조회수를 올리고 렌더링하여 보여줍니다. 일치하는 id가 데이터베이스에 존재하지 않는다면 홈으로 리다이렉트합니다.
     */
    @GetMapping("/detail/{id}")
    public String viewArticle(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Article> optionalArticle = articleDatabase.findBy(id);
        if (optionalArticle.isEmpty()) {
            return "redirect:/";
        }
        Article article = optionalArticle.get();
        article.increaseViews();
        articleDatabase.update(article);

        model.addAttribute("article", article);

        User loginUser = LoginUserProvider.provide(session);
        if (loginUser.hasSameNickname(article.getWriter())) {
            model.addAttribute("isLoginUser", true);
        }

        return "article/show";
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Optional<Article> optionalArticle = articleDatabase.findBy(id);
        if (optionalArticle.isEmpty()) {
            return "redirect:/";
        }
        Article article = optionalArticle.get();
        ArticleWriteForm articleWriteForm = new ArticleWriteForm(article.getTitle(), article.getContent());

        model.addAttribute("articleWriteForm", articleWriteForm);
        return "article/update";
    }

    @PutMapping("/edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute ArticleWriteForm articleWriteForm) {
        Optional<Article> optionalArticle = articleDatabase.findBy(id);
        if (optionalArticle.isEmpty()) {
            return "redirect:/";
        }
        Article targetArticle = optionalArticle.get();
        Article updateArticle = targetArticle.update(articleWriteForm.getTitle(), articleWriteForm.getContent());
        articleDatabase.update(updateArticle);

        logger.info("게시글 정보가 업데이트 되었습니다. {}", updateArticle);
        return "redirect:/articles/detail/" + id;
    }

}
