package codesquad.springcafe.Controller;

import codesquad.springcafe.Domain.Post;
import codesquad.springcafe.Service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public String findPost(@PathVariable("postId") String postId, Model model) {
        Post post = postService.findPostById(Long.parseLong(postId));
        model.addAttribute("post", post);
        return "post/show";
    }

    @PostMapping
    public String post(Post post) {
        // todo: 데이터에 저장하는 메서드 작성
        postService.savePost(post);
        return "redirect:/index";
    }
}
