
package codesquad.springcafe.controller;

import codesquad.springcafe.dto.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    @GetMapping("/create")
    public String showArticleForm() {
        return "qna/form";
    }

    // 게시글 작성 처리
    @PostMapping("/show")
    public String createArticle(@ModelAttribute Article article, RedirectAttributes redirectAttributes) {

        return "qna/show";
    }

}


