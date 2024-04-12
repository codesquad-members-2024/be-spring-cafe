package codesquad.springcafe.controller;

import codesquad.springcafe.DB.Database;
import codesquad.springcafe.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(value = {"/user", "/user/form.html"})
    public String showForm() {
        return "user/form";
    }

    @PostMapping("/user")
    public String register(User user) {
        Database.addUser(user);
        logger.debug("new user: " + user.toString());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showList(Model model) {
        model.addAttribute("users", Database.getAllUsers());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(Model model, @PathVariable("userId") String userId) {
        model.addAttribute("user", Database.getUser(userId));
        return "user/profile";
    }
}
