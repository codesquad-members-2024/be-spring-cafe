package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

//    회원가입 html 보여줌
    @GetMapping("/users")
    public String join() {
        return "user/form";
    }

//  회원가입 버튼 누를시 작동
    @PostMapping("/users")
    public String join(@ModelAttribute User user) {
        service.join(user);
        return "redirect:/users/list";
    }

//    회원리스트 폼 보여줌
    @GetMapping("/users/list")
    public String users(Model model) {
        List<User> users = service.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = service.findUserById(userId).get();
        model.addAttribute("user", user);
        return "user/profile";
    }

}
