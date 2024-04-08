package codesquad.springcafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @GetMapping("/write")
    public String writeForm() {
        return "post/form";
    }

    @GetMapping("/show")
    public String showForm() {
        return "post/show";
    }
}
