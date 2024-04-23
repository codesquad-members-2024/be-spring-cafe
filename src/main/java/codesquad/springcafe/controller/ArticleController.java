package codesquad.springcafe.controller;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import codesquad.springcafe.dto.article.ArticleUpdateDTO;
import codesquad.springcafe.dto.article.UploadDTO;
import codesquad.springcafe.dto.user.UserInfoDTO;
import codesquad.springcafe.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    public String upload(@ModelAttribute("article") UploadDTO uploadDTO, Model model) {
        ArticleInfoDTO newArticle = articleService.upload(uploadDTO);
        model.addAttribute("article", newArticle);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showList(Model model) {
        List<ArticleInfoDTO> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("articles/{index}")
    public String showArticle(@PathVariable Long index, Model model) {
        ArticleInfoDTO targetArticle = articleService.findByIndex(index);
        model.addAttribute("article", targetArticle);
        return "/article/show";
    }
}
