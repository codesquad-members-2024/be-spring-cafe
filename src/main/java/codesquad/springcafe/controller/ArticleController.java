package codesquad.springcafe.controller;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.form.article.ArticleAddForm;
import codesquad.springcafe.model.Article;
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
        ArticleAddForm articleAddForm = new ArticleAddForm("", "");
        model.addAttribute("articleAddForm", articleAddForm);
        return "article/form";
    }

    /**
     * 사용자가 작성한 아티클을 생성하고 데이터베이스에 저장합니다.
     */
    @PostMapping("/add")
    public String addForm(@Validated @ModelAttribute ArticleAddForm articleAddForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("errors ={}", bindingResult);
            return "article/form";
        }

        Article article = new Article("익명", articleAddForm.getTitle(), articleAddForm.getContent());
        articleDatabase.add(article);
        logger.info("새로운 게시물이 추가되었습니다. {}", article);
        return "redirect:/";
    }

    /**
     * 사용자가 요청한 id의 아티클을 조회수를 올리고 렌더링하여 보여줍니다. 일치하는 id가 데이터베이스에 존재하지 않는다면 홈으로 리다이렉트합니다.
     */
    @GetMapping("/detail/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        Optional<Article> optionalArticle = articleDatabase.findBy(id);
        if (optionalArticle.isEmpty()) {
            return "redirect:/";
        }
        Article article = optionalArticle.get();
        article.increaseViews();
        articleDatabase.update(article);

        model.addAttribute("article", article);

        return "article/show";
    }
}
