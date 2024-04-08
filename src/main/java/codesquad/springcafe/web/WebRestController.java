package codesquad.springcafe.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebRestController {

    @GetMapping("/users")
    public String hello() {
        return "/user/form";
    }
}
