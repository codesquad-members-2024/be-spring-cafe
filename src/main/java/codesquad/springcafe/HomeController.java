package codesquad.springcafe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("/list")
    public String list() {
        return "user/list";
    }
}
