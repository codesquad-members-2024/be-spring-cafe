package codesquad.springcafe.controller;

import codesquad.springcafe.model.User;
import codesquad.springcafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String getUserList(Model model) {
        log.debug("user list");
        model.addAttribute("users", userService.findMembers());
        return "user/list";
    }

    @PostMapping("/users")
    public String register(
            @ModelAttribute User user,
            Model model
    ) {
        log.debug("register");

        userService.join(user);
        model.addAttribute("models", userService.findMembers());
        return "redirect:/users";
    }
}
