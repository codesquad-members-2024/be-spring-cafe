package codesquad.springcafe.controller;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import codesquad.springcafe.dto.article.ArticleUploadDTO;
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
    public String upload(@ModelAttribute("article") ArticleUploadDTO articleUploadDTO, Model model) {
        ArticleInfoDTO newArticle = articleService.upload(articleUploadDTO);
        model.addAttribute("article", newArticle);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showList(Model model) {
        List<ArticleInfoDTO> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("articles/{id}")
    public String showArticle(@PathVariable("id") Long id, Model model) {
        ArticleInfoDTO targetArticle = articleService.findById(id);
        model.addAttribute("article", targetArticle);
        return "/qna/show";
    }
}
