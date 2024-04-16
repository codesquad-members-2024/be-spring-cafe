package codesquad.springcafe.main;

import codesquad.springcafe.post.PostService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final PostService postService;

    public MainController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"/", "/index.html"})
    public String showRegistrationForm(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "/index";
    }
}
