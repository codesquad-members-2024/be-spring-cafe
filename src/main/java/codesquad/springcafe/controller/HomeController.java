package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Post;
import codesquad.springcafe.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final PostRepository repository;

    @Autowired
    public HomeController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Post> posts = repository.findAll();
        model.addAttribute("posts", posts);
        return "index";
    }
}
