package codesquad.springcafe.article;

import codesquad.springcafe.article.repository.ArticleRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @PostMapping("")
    public String postArticle(@ModelAttribute ArticlePostReq articlePostReq){
        articleRepository.add(articlePostReq);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showArticle(@PathVariable("id") int id, Model model, HttpServletResponse response) {
        Article article = articleRepository.findById(id);

        // 존재하지 않는 게시글
        if(article == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        // 정상 흐름
        articleRepository.addPoint(article);
        model.addAttribute("article", article);
        return "qna/show";
    }

    @GetMapping("/form")
    public String articleForm(){
        return "qna/form";
    }
}
