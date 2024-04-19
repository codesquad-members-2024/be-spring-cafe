package codesquad.springcafe.controller;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import codesquad.springcafe.dto.article.UploadDTO;
import codesquad.springcafe.service.ArticleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions")
    public String upload(@ModelAttribute("article") UploadDTO uploadDTO, Model model) {
        ArticleInfoDTO newArticle = articleService.upload(uploadDTO);
        model.addAttribute("article", newArticle);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showList(Model model) {
        List<ArticleInfoDTO> articles = articleService.findAllArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("articles/{index}")
    public String showArticle(@PathVariable Long index, Model model) {
        ArticleInfoDTO targetArticle = articleService.findArticleByIndex(index);
        model.addAttribute("article", targetArticle);
        return "/qna/show";
    }
}
