package codesquad.springcafe.controller;

import codesquad.springcafe.service.UserService;
import codesquad.springcafe.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/create")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "/users/form";
    }
    @GetMapping("/list")
    public String showUserListForm(Model model){
        model.addAttribute("users",userService.findAll());
        return "/users/list";
    }
    @PostMapping("/create")
    public String createUser(User user)
    {
        userService.createUser(user);
        return "redirect:/users/list";
    }
    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable("userId") String userId, Model model) {
        User user = userService.findByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "users/profile";
        }
        return "redirect:/users/list";
    }
}
