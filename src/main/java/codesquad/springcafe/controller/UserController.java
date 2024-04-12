package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService service;


    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String join() {
        return "user/form";
    }

    @PostMapping("/users")
    public String join(@ModelAttribute User user) {
        service.join(user);
        return "redirect:users/list";
    }

    @GetMapping("/users/list")
    public String users(Model model) {
        List<User> users = service.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

}
