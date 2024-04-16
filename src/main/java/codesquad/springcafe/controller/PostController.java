package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostController {
    //    게시글 폼 보여줌
    @GetMapping
    public String postForm() {
        return "qna/form";
    }

    @PostMapping
    public String createPost(@ModelAttribute Post post) {
        return "redirect:/";
    }

}
