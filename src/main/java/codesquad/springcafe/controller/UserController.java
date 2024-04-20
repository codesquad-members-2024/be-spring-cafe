package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

//    회원가입 html 보여줌
    @GetMapping("/users")
    public String join() {
        return "user/form";
    }

//    회원가입 버튼 누를시 작동
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

    @GetMapping("/users/{id}")
    public String profile(@PathVariable String id, Model model) {
        User user = service.findUserById(id).get();
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{id}/update")
    public String profileUpdate(@PathVariable String id, Model model) {
        User user = service.findUserById(id).get();
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/users/{id}/update")
    public String profileUpdate(@ModelAttribute User user) {
        service.update(user);
        return "redirect:/";
    }

}
