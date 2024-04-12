package codesquad.springcafe.users.controller;

import codesquad.springcafe.exception.PasswordMismatchException;
import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.users.service.UserService;
import model.User;
import model.UserData;
import model.UserUpdateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

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
        model.addAttribute("userName", userData.getName());
        return "/user/login_success";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        User user = userService.findUserById(userId);

        model.addAttribute("user", user);

        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String getUserEditPage(@PathVariable String userId, Model model) {
        User user = userService.findUserById(userId);

        model.addAttribute("user", user);

        return "/user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, UserUpdateData updateData, Model model) {
        try {
            userService.updateUser(userId, updateData);
        } catch (PasswordMismatchException e) {
            logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            User user = userService.findUserById(userId);
            model.addAttribute("user", user);
            model.addAttribute("error", true);
            return "/user/updateForm";
        }
        return "redirect:/users";
    }

}
