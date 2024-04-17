package codesquad.springcafe.post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/qna")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/new")
    public String showPostForm(Model model) {
        model.addAttribute("post", new Post());
        logger.info("Showing post form");
        return "qna/form";
    }

    @PostMapping("/new")
    public String createPost(Post post) {
        postService.createPost(post);
        logger.info("Post created {}", post);
        return "redirect:/";
    }

    @GetMapping("/{postId}")
    public String showPostDetails(@PathVariable("postId") long postId, Model model) {
        Post post = postService.findByPostId(postId);
        if (post != null) {
            logger.info("Showing post details for {}", post);
            model.addAttribute("nlString", System.lineSeparator());
            model.addAttribute("post", post);
            return "qna/show";
        }
        return "redirect:/";
    }
}
