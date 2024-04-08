package codesquad.springcafe.controller;

import codesquad.springcafe.database.UserDatabase;
import codesquad.springcafe.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/*")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserDatabase userDatabase;

    public UserController(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @GetMapping("/add")
    public String addUserForm() {
        return "/user/form";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userDatabase.save(user);
        logger.info("새로운 유저가 생성되었습니다. {}", user);
        return "redirect:/users";
    }
}
