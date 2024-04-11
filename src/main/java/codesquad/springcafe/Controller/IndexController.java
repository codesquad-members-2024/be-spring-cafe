package codesquad.springcafe.Controller;

import codesquad.springcafe.Domain.Post;
import codesquad.springcafe.Service.PostService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/index"})
public class IndexController {

    private final PostService postService;

    public IndexController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String index(Model model) {
        List<Post> posts = postService.findAllPost();
        model.addAttribute("posts", posts);
        return "index";
    }
}
