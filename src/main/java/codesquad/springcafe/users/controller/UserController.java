package codesquad.springcafe.users.controller;

import codesquad.springcafe.exception.PasswordMismatchException;
import codesquad.springcafe.users.repository.UserRepository;
import model.user.User;
import model.user.UserData;
import model.user.UserUpdateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showUsers(Model model) {
        ArrayList<User> users = userRepository.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("totalUsers", users.size());

        return "/user/list";
    }

    @PostMapping
    public String registerUser(UserData userData, Model model) {
        userRepository.createUser(userData);

        model.addAttribute("userEmail", userData.getEmail());
        model.addAttribute("userId", userData.getUserId());
        model.addAttribute("userName", userData.getName());
        return "/user/login_success";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        User user = userRepository.findUserById(userId);

        model.addAttribute("user", user);

        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String getUserEditPage(@PathVariable String userId, Model model) {
        User user = userRepository.findUserById(userId);

        model.addAttribute("user", user);

        return "/user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, UserUpdateData updateData, Model model) {
        try {
            userRepository.updateUser(userId, updateData);
        } catch (PasswordMismatchException e) {
            logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            User user = userRepository.findUserById(userId);
            model.addAttribute("user", user);
            model.addAttribute("error", true);
            return "/user/updateForm";
        }
        return "redirect:/users";
    }

}
