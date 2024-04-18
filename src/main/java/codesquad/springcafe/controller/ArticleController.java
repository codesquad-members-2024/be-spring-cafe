package codesquad.springcafe.controller;

import codesquad.springcafe.DB.ArticleDatabase;
import codesquad.springcafe.DB.MemoryArticleDatabase;
import codesquad.springcafe.DB.UserDatabase;
import codesquad.springcafe.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleDatabase articleDatabase;

    @Autowired
    public ArticleController(@Qualifier("MemoryArticleDatabase") ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    @GetMapping("/main")
    public String showArticle(Model model) {
        model.addAttribute("articles", articleDatabase.getAllArticles()); // 전체 article 반환

        return "main";
    }

    @PostMapping("/article")
    // @ModelAttribute 어노테이션 을 통해 Post body를 파싱해 article 객체로 반환한다.
    public String saveArticle(@ModelAttribute Article article, Model model) {
        article.setId(Integer.toString(articleDatabase.getArticleSize()+1)); // article id 설정
        articleDatabase.saveArticle(article);
        logger.debug("new article: " + article.toString());

        // article를 저장 후 main 페이지로 redirect
        return "redirect:/main";
    }

    // 게시글 상세 페이지
    @GetMapping("/article/{id}")
    public String showArticle(@PathVariable Integer id, Model model) {
        if (articleDatabase.isArticleEmpty()){
            return "redirect:/main";
        }

        Article article = articleDatabase.getArticleById(id);
        model.addAttribute("article", article); // 해당 id에 맞는 article 반환

        return "/article/show";
    }
}
