package codesquad.springcafe.controller;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/question")
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String getQuestionForm() {
        return "qna/form";
    }

    @PostMapping
    public String writeQuestion(@ModelAttribute ArticleRequestDto articleRequestDto) {
        articleRepository.save(articleRequestDto);
        return "redirect:/";
    }
}
