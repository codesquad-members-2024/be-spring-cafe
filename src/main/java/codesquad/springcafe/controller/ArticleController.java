package codesquad.springcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    @GetMapping("/{id}")
    public String showArticle(@PathVariable("id") int id){
        // findByID

        return "qna/show";
    }
}
