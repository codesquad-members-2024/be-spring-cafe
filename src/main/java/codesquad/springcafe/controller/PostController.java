package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Post;
import codesquad.springcafe.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostRepository repository;

    @Autowired
    public PostController(PostRepository repository) {
        this.repository = repository;
    }

//    게시글 폼 보여줌
    @GetMapping
    public String postForm() {
        return "qna/form";
    }

//    게시글 저장
    @PostMapping
    public String createPost(@ModelAttribute Post post) {
        repository.save(post);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable String id, Model model) {
        Post post = repository.findById(Integer.parseInt(id));
        model.addAttribute("post", post);
        return "qna/show";
    }
}
