package codesquad.springcafe.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository repository) {
        articleRepository = repository;
    }

    @PostMapping("/article")
    public String storeArticle(@ModelAttribute Article article) {
        log.debug("들어온 게시글 : {}", article.toString());
        articleRepository.save(article);
        return "redirect:/";
    }
}
