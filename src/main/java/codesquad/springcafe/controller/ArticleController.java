package codesquad.springcafe.controller;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleCreateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleDatabase articleDatabase;

    @Autowired
    public ArticleController(@Qualifier("H2ArticleDatabase") ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    @GetMapping("/main")
    public String showArticle(Model model) {
        model.addAttribute("articles", articleDatabase.getAllArticles()); // 전체 article 반환

        return "main";
    }

    @PostMapping("/article")
    // @ModelAttribute 어노테이션 을 통해 Post body를 파싱해 articleCreateDto 객체로 반환한다.
    public String saveArticle(@ModelAttribute ArticleCreateDto articleCreateDto) {
        Article newArticle = articleCreateDto.makeArticle(); // dto Article로 변환
        articleDatabase.saveArticle(newArticle);
        logger.debug("new article: " + newArticle.toString());

        // article를 저장 후 main 페이지로 redirect
        return "redirect:/main";
    }

    // 게시글 상세 페이지
    @GetMapping("/article/{id}")
    public String showArticle(@PathVariable int id, Model model) {
        if (articleDatabase.isArticleEmpty()){
            return "redirect:/main";
        }

        Article article = articleDatabase.getArticleById(id);
        model.addAttribute("article", article); // 해당 id에 맞는 article 반환

        return "/article/show";
    }
}
