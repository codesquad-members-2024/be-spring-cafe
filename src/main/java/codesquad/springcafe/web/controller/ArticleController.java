package codesquad.springcafe.web.controller;

import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.web.dto.ArticleCreateDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/create")
    public String quest(Model model) {
        model.addAttribute("article", new ArticleCreateDto());
        return "/qna/form";
    }

    @PostMapping("/articles/create")
    public String quest(@ModelAttribute("article") ArticleCreateDto articleCreateDto, BindingResult bindingResult) {
        if (!StringUtils.hasText(articleCreateDto.getWriter())) {
            bindingResult.addError(new FieldError("article", "writer", articleCreateDto.getWriter(),
                    false, null, null, "작성자를 입력하세요."));
        }
        if (!StringUtils.hasText(articleCreateDto.getTitle())) {
            bindingResult.addError(new FieldError("article", "title", articleCreateDto.getTitle(),
                    false, null, null, "제목을 입력하세요."));
        }
        if (!StringUtils.hasText(articleCreateDto.getContents())) {
            bindingResult.addError(new FieldError("article", "contents", articleCreateDto.getContents(),
                    false, null, null, "내용을 입력하세요."));
        }
        if (bindingResult.hasErrors()) {
            return "/qna/form";
        }
        articleService.saveArticle(articleCreateDto);
        return "redirect:/";
    }

    @GetMapping("/articles/{sequence}")
    public String articleDetails(@PathVariable int sequence, Model model) {
        model.addAttribute("nlString", System.lineSeparator());
        model.addAttribute("article", articleService.findBySequence(sequence));
        return "/qna/show";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("articles", articleService.getArticles());
        return "/index";
    }
}