package codesquad.springcafe.post;

import jakarta.servlet.http.HttpSession;
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
        return "qna/form";
    }

    @PostMapping("/new")
    public String createPost(Post post, HttpSession session) {
        String userId = (String) session.getAttribute("loggedUser");
        postService.createPost(userId, post);
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

    @GetMapping("/{postId}/update")
    public String showUpdatePost(@PathVariable("postId") long postId, Model model, HttpSession session) {
        Post post = postService.findByPostId(postId);
        String userId = (String) session.getAttribute("loggedUser");

        if (post.getAuthor().equals(userId)) {
            model.addAttribute("post", post);
            return "qna/updatepost";
        }else{
            return "redirect:/qna/updatefail";
        }
    }

    @PutMapping("/{postId}/update")
    public String updatePost(@PathVariable("postId") long postId, Post post) {
        postService.updatePost(postId, post);
        return "redirect:/";
    }

    @DeleteMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") long postId, HttpSession session) {
        Post post = postService.findByPostId(postId);
        String userId = (String) session.getAttribute("loggedUser");

        if (post.getAuthor().equals(userId)) {
            postService.deletePost(postId);
            return "redirect:/";
        }else{
            return "redirect:/qna/updatefail";
        }
    }

    @GetMapping("/updatefail")
    public String showUpdateFailForm() {
        return "qna/updatefail";
    }
}
