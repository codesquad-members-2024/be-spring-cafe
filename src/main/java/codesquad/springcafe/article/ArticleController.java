package codesquad.springcafe.article;

import codesquad.springcafe.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @PostMapping("")
    public String postArticle(@ModelAttribute Article article){
        articleRepository.add(article);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showArticle(@PathVariable("id") int id, Model model) {
        Article article = articleRepository.findById(id);
        article.addPoint();

        model.addAttribute("article", article);
        return "qna/show";
    }

    @GetMapping("/form")
    public String articleForm(){
        return "qna/form";
    }
}
