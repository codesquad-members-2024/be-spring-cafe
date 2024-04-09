package codesquad.springcafe.controller;

import codesquad.springcafe.service.UserService;
import codesquad.springcafe.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/user/create")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "/user/form";
    }
    @GetMapping("/user/list")
    public String showUserListForm(Model model){
        model.addAttribute("users",userService.findAll());
        return "/user/list";
    }
    @PostMapping("/user/create")
    public String createUser(User user)
    {
        userService.createUser(user);
        return "redirect:/user/list";
    }
    @GetMapping("/users/{userId}")
    public String getUserProfile(@PathVariable("userId") String userId, Model model) {
        User user = userService.findByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "user/profile";
        }
        return "redirect:/users/list";
    }



}
