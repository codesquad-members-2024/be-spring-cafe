package codesquad.springcafe.Controller;

import codesquad.springcafe.Domain.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("qna")
public class ArticleController {

    @PostMapping
    public String qna(Article article) {

    }
}
