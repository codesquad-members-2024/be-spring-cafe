package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Post;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.service.PostService;
import jakarta.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public String findPost(@PathVariable("postId") String postId, Model model,
        HttpSession httpSession) {
        Post post = postService.findPostById(Long.parseLong(postId));
        String userId = (String) httpSession.getAttribute("loginUserId");

        model.addAttribute("post", post);
        if (userId.equals(post.getUserId())) {
            model.addAttribute("author", true);
        } else {
            model.addAttribute("author", false);
        }
        return "post/show";
    }

    @PostMapping
    public String post(Post post) {
        // todo: 데이터에 저장하는 메서드 작성
        postService.savePost(post);
        return "redirect:/index";
    }

    @GetMapping("/{postId}/update")
    public String update(@PathVariable("postId") String postId, Model model,
        HttpSession httpSession) {
        String loginUserId = (String) httpSession.getAttribute("loginUserId");

        try {
            Post post = postService.findPostById(Long.parseLong(postId));
            if (loginUserId.isEmpty() || !loginUserId.equals(post.getUserId())) {
                return "redirect:/";
            }

            model.addAttribute("post", post);
        } catch (NoSuchElementException e) {
            //존재하지 않는 Post 리다이렉트
            logger.error(e.getMessage());
            return "redirect:/";
        }
        return "post/update";
    }

    @PutMapping("/update")
    public String update(@ModelAttribute Post post, HttpSession httpSession) {
        String loginUserId = (String) httpSession.getAttribute("loginUserId");

        //유저 정보가 없거나, 유저아이디와 변경할 아이디가 같지 않은경우 에러페이지 출력
        if (loginUserId.isEmpty() || !loginUserId.equals(post.getUserId())) {
            return "redirect:/index";
        }

        try {
            postService.update(post);
            return "redirect:/post/" + post.getId();
        } catch (Exception e) {
            logger.error("post update failed: {}", e.getMessage());
            return "redirect:/index";
        }
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String postId, HttpSession httpSession) {

        String loginUserId = (String) httpSession.getAttribute("loginUserId");
        Post post = postService.findPostById(Long.parseLong(postId));

        if (loginUserId.isEmpty() || !loginUserId.equals(post.getUserId())) {
            //유저와 포스트 작성자가 같지 않으면 포스트 아이디 출력
            return "redirect:/post/"+postId;
        }

        postService.deletePost(postId);
        return "redirect:/";
    }
}
