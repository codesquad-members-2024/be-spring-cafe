package codesquad.springcafe.web.controller;

import codesquad.springcafe.web.domain.Article;
import codesquad.springcafe.web.domain.User;
import codesquad.springcafe.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/new")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/user/new")
    public String createUser(@ModelAttribute User user) {

        userService.join(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String userList(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/profile/{userId}")
    public String userProfileForm(@PathVariable String userId, Model model) {
        Optional<User> user = userService.findUser(userId);
        user.ifPresent(u -> model.addAttribute("user", u));

        return "user/profile";
    }

    @GetMapping("/user/update/{userId}")
    public String articleUpdateForm(@PathVariable String userId, Model model) {

        Optional<User> optionalUser = userService.findUser(userId);

        optionalUser.ifPresent(user -> model.addAttribute("user", user));
        return "user/updateForm";
    }

    @PutMapping("/user/update/{userId}")
    public String userUpdate(@PathVariable String userId, @ModelAttribute User updatedUser, Model model) {
        Optional<User> optionalUser = userService.findUser(userId);
        optionalUser.ifPresent(user -> {
            user.setName(updatedUser.getName());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());

            userService.userUpdate(user);
                }
        );
        return "redirect:/user/list";
    }
}
