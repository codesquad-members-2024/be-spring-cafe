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

    @GetMapping("articles/{index}/update")
    public String tryUpdate(@PathVariable Long index, HttpSession session, Model model) {
        ArticleInfoDTO article = articleService.findByIndex(index);
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("loggedInUser");
        if (!article.isWriter(user.getUserId())) {
            return "/article/update_failed";
        }
        return "redirect:/articles/" + index + "/form";
    }

    @GetMapping("articles/{index}/form")
    public String showUpdateForm(@PathVariable Long index, Model model) {
        ArticleInfoDTO article = articleService.findByIndex(index);
        model.addAttribute("article", article);
        return "/article/updateForm";
    }

    @PutMapping("articles/{index}/update")
    public String updateInfo(@ModelAttribute("article") ArticleUpdateDTO updateInfo, @PathVariable Long index, Model model) {
        ArticleInfoDTO updatedArticle = articleService.updateInfo(index, updateInfo);
        model.addAttribute("article", updatedArticle);
        return "redirect:/";
    }

    @DeleteMapping("articles/{index}")
    public String delete(@PathVariable Long index, HttpSession session) {
        ArticleInfoDTO article = articleService.findByIndex(index);
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("loggedInUser");
        if (!article.isWriter(user.getUserId())) {
            return "/article/delete_failed";
        }
        articleService.delete(index);
        return "redirect:/";
    }
}
