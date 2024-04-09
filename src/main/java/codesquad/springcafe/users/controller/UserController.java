package codesquad.springcafe.users.controller;

import codesquad.springcafe.users.service.UserService;
import model.User;
import model.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(Model model) {
        ArrayList<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("totalUsers", users.size());

        return "/user/list";
    }

    @PostMapping
    public String registerUser(UserData userData, Model model) {
        userService.createUser(userData);

        model.addAttribute("userEmail", userData.getEmail());
        model.addAttribute("userId", userData.getUserId());

        return "/user/login_success";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        model.addAttribute("user", user);

        return "/user/profile";
    }

}
