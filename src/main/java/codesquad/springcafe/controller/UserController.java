package codesquad.springcafe.controller;

import codesquad.springcafe.model.User;
import codesquad.springcafe.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String signUp(@ModelAttribute("user") User user, Model model) {
        User newUser = userService.createUser(user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
        model.addAttribute("user", newUser);
        return "redirect:/users";
    }

    @GetMapping
    public String showUserList(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String showUserProfile(@PathVariable String userId,
        Model model) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return "/user/profile";
    }
}