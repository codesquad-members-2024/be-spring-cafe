package codesquad.springcafe.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository repository) {
        articleRepository = repository;
    }

    @PostMapping("/article")
    public String storeArticle(@ModelAttribute ArticleDto articleDto) {
        final String writer = articleDto.getWriter();
        final String title = articleDto.getTitle();
        final String contents = articleDto.getContents();
        final LocalDateTime createAt = LocalDateTime.now();
        Article article = new Article(writer, title, contents, createAt);

        log.debug("들어온 게시글 : {}", article);
        articleRepository.save(article);
        return "redirect:/articles";
    }

    @GetMapping("/articles")
    public String showArticle(Model model) {
        Collection<Article> allArticles = articleRepository.findAll();

        AtomicLong atomicLong = new AtomicLong(0L);
        List<ArticleDto> articles = allArticles.stream()
                .map(article -> {
                    String writer = article.getWriter();
                    String title = article.getTitle();
                    String contents = article.getContents();
                    LocalDateTime createAt = article.getCreateAt();
                    ArticleDto dto = new ArticleDto(writer, title, contents);
                    dto.setPoint(atomicLong.incrementAndGet());
                    dto.setCreateAt(createAt);
                    return dto;
                }).toList();

        model.addAttribute("articles", articles);
        return "index";
    }
}
