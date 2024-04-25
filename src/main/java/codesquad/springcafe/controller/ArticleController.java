package codesquad.springcafe.controller;

import codesquad.springcafe.db.article.ArticleDatabase;
import codesquad.springcafe.model.article.Article;
import codesquad.springcafe.model.article.dto.ArticleCreationDto;
import codesquad.springcafe.model.article.dto.ArticleProfileDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

import static codesquad.springcafe.controller.LoginController.LOGIN_SESSION_NAME;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleDatabase articleDatabase;

    @Autowired
    public ArticleController(ArticleDatabase articleDatabase){
        this.articleDatabase = articleDatabase;
    }

    @GetMapping("/add")
    public String getArticleCreationForm(){
        return "article/form";
    }

    @PostMapping("/add")
    public String createArticle(@ModelAttribute ArticleCreationDto articleCreationDto, HttpSession session) {
        Article article = articleCreationDto.toEntity();
        article.setSequence();
        article.setWriter((String) session.getAttribute(LOGIN_SESSION_NAME));
        articleDatabase.addArticle(article);
        return "redirect:/";
    }


    @GetMapping("/detail/{sequence}")
    public String loadArticleContent(@PathVariable long sequence,
                                     Model model,
                                     HttpServletResponse response) throws IOException {
        Optional<ArticleProfileDto> articleProfile = articleDatabase.findArticleBySequence(sequence);
        if(articleProfile.isEmpty()){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        model.addAttribute("article", articleProfile.get());
        return "article/show";
    }
}