package codesquad.springcafe.web.controller;

import codesquad.springcafe.web.domain.User;
import codesquad.springcafe.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Optional;

@Controller
public class UserProfileController {
    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/profile/{userName}")
    public String userProfile(@PathVariable String userName, Model model) {
        System.out.println("userName = " + userName);
        Optional<User> user = userService.findUser(userName);
        user.ifPresent(u -> model.addAttribute("user", u));

        return "user/profile";
    }
}