package codesquad.springcafe.controller;

import codesquad.springcafe.database.UserDatabase;
import codesquad.springcafe.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @GetMapping("/users")
    public String getUserList() {
        System.out.println("user list");
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
        return "redirect:/user/list.html";
    }
}
