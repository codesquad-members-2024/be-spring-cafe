package codesquad.springcafe.controller;

import codesquad.springcafe.model.User;
import codesquad.springcafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getUserList(Model model) {
        log.info("user list");
        model.addAttribute("users", userService.findAllUsers());
        return "user/list";
    }

    @PostMapping
    public String register(@ModelAttribute User user) {
        log.info("register");
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/{userSequence}")
    public String getUser(@PathVariable Long userSequence, Model model) {
        log.info("get User : {}", userSequence);
        User user = userService.findUserBySequence(userSequence);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/join")
    public String getSignUpForm() {
        return "/user/form";
    }
}
