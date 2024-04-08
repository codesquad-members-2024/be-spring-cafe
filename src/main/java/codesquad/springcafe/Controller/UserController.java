package codesquad.springcafe.Controller;

import codesquad.springcafe.Domain.User;
import codesquad.springcafe.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


//file : Controller
@Controller
public class UserController {

    UserService userService = new UserService();
    @GetMapping("/user/form.html")
    public String form() {
        return "user/form";
    }

    @GetMapping("/users")
    public String users() {
        return "user/list";
    }
    @PostMapping("/users")
    public String users(@ModelAttribute User user) {
        userService.signUp(user);
        return "redirect:/users";
    }
}