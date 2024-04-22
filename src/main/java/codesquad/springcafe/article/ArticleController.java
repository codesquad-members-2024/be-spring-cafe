package codesquad.springcafe.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    // 아티클 id를 가지고 해당 아티클 보여주기 없으면 기본 홈페이지 보여주기
    @GetMapping("/article/{id}")
    public String showArticle(@PathVariable int id, Model model) {
        return service.findArticle(id)
                .map(article -> {
                    model.addAttribute("article", article);
                    return "qna/show";
                }).orElseThrow(() -> new IllegalArgumentException(id + "는 찾을 수 없습니다"));
    }


    // 아티클 등록
    @PostMapping("/article")
    public String storeArticle(@ModelAttribute ArticleCraetionDto articleCraetionDto) {
        service.save(articleCraetionDto);
        return "redirect:/articles";
    }

    // 모든 아티클 보여주기
    @GetMapping("/articles")
    public String showArticle(Model model) {
        model.addAttribute("articles", service.getAllArticles());
        return "index";
    }
}
