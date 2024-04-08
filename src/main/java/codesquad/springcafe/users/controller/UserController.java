package codesquad.springcafe.users.controller;

import codesquad.springcafe.users.service.UserService;
import db.UserDatabase;
import model.User;
import model.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showUserForm() {
        return "/user/form";
    }

    @PostMapping
    public String registerUser(UserVO userVO) {
        userService.createUser(userVO);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String showUsers(Model model) {
        ArrayList<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("totalUsers", users.size());

        return "/user/list";
    }

}
