package codesquad.springcafe.controller;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import codesquad.springcafe.dto.article.ArticleUploadDTO;
import codesquad.springcafe.dto.article.ArticleUpdateDTO;
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
        return "/article/show";
    }

    @GetMapping("articles/{id}/update")
    public String tryUpdate(@PathVariable Long id, HttpSession session, Model model) {
        ArticleInfoDTO article = articleService.findById(id);
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("loggedInUser");
        if (!article.isWriter(user.getUserId())) {
            return "/article/update_failed";
        }
        return "redirect:/articles/" + id + "/form";
    }

    @GetMapping("articles/{id}/form")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        ArticleInfoDTO article = articleService.findById(id);
        model.addAttribute("article", article);
        return "/article/updateForm";
    }

    @PutMapping("articles/{id}/update")
    public String updateInfo(@ModelAttribute("article") ArticleUpdateDTO updateInfo, @PathVariable Long id, Model model) {
        ArticleInfoDTO updatedArticle = articleService.updateInfo(id, updateInfo);
        model.addAttribute("article", updatedArticle);
        return "redirect:/";
    }

    @DeleteMapping("articles/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        ArticleInfoDTO article = articleService.findById(id);
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("loggedInUser");
        if (!article.isWriter(user.getUserId())) {
            return "/article/delete_failed";
        }
        articleService.delete(id);
        return "redirect:/";
    }
}
