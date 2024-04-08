package codesquad.springcafe.controller;

import codesquad.springcafe.database.UserDatabase;
import codesquad.springcafe.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public String getUserList() {
        log.debug("user list");
        return "/user/list.html";
    }

    @PostMapping("/users")
    public String register(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ) {
        User user = new User(userId, password, name, email);
        UserDatabase.saveUser(user);
        log.debug("user saved: {}", user.getUserId());
        return "redirect:/user/list.html";
    }
}
