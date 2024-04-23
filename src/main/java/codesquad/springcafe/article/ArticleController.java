package codesquad.springcafe.article;

import codesquad.springcafe.article.dto.ArticleUpdateRequestDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    // 아티클 id를 가지고 해당 아티클 보여주기 없으면 기본 홈페이지 보여주기
    @GetMapping("/article/{id}")
    public String showArticle(@PathVariable int id, Model model, HttpSession session) {
        String value = (String) session.getAttribute("sessionUserId");
        if (value == null) {
            return "redirect:/form/login";
        }

        return service.findArticle(id)
                .map(article -> {
                    model.addAttribute("article", article);
                    return "qna/show";
                }).orElseThrow(() -> new IllegalArgumentException(id + "는 찾을 수 없습니다"));
    }

    // 아티클 등록
    @PostMapping("/article")
    public String saveArticle(@ModelAttribute ArticleCraetionDto articleCraetionDto, HttpSession session) {
        String writer = (String) session.getAttribute("sessionUserId");
        service.save(articleCraetionDto, writer);
        return "redirect:/articles";
    }

    // 모든 아티클 보여주기
    @GetMapping("/articles")
    public String showArticle(Model model) {
        model.addAttribute("articles", service.getAllArticles());
        return "index";
    }

    @GetMapping("/qna/form")
    public String showArticleRegisterForm(HttpSession session) {
        String value = (String) session.getAttribute("sessionUserId");
        if (value == null) {
            return "redirect:/form/login";
        }
        return "qna/form";
    }

    @GetMapping("/qna/{writer}/form")
    public String showUpdateForm(@PathVariable String writer, HttpSession session) {
        String value = (String) session.getAttribute("sessionUserId");
        if (writer.equals(value)) {
            return "qna/updateForm";
        }
        return "qna/accessFailed";
    }

    @PutMapping("/qna/{writer}")
    public String updateArticle(@PathVariable String writer, @Valid ArticleUpdateRequestDto dto) {
        service.updateArticle(writer, dto);
        return "redirect:/";
    }

    @DeleteMapping("/qna/{writer}/{id}")
    public String deleteArticle(@PathVariable String writer, @PathVariable Long id) {
        service.deleteArticle(writer, id);
        return "redirect:/";
    }
}
