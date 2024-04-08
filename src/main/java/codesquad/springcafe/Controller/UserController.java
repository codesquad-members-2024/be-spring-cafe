package codesquad.springcafe.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//file : Controller
@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }
}