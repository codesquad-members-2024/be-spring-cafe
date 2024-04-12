package codesquad.springcafe.controller;

import codesquad.springcafe.dto.UpdatedUser;
import codesquad.springcafe.dto.User;
import codesquad.springcafe.service.UserService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/join")
    public String showJoinPage() {
        return "user/join";
    }

    @PostMapping("/join")
    public String processJoinForm(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/users/list";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String processLoginForm() {
        // TODO: login 기능
        return null;
    }

    @GetMapping("/list")
    public String showListPage(Model model) {
        List<User> users = userService.findAllUser();
        model.addAttribute("userList", users);
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String showProfilePage(Model model, @PathVariable String userId) {
        Optional<User> user = userService.findUserByUserId(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/profile";
        }
        return "error/not_found";
    }

    @GetMapping("/match-pw/{userId}")
    public String showMatchPasswordPage(Model model, @PathVariable String userId) {
        model.addAttribute("userId", userId);
        return "user/match_pw";
    }

    @GetMapping("/update/{userId}")
    public String showUpdatePage(Model model, @PathVariable String userId) {
        model.addAttribute("userId", userId);
        return "user/update";
    }

    @PutMapping("/update/{userId}")
    public String updateUserInfo(@ModelAttribute UpdatedUser updatedUser, @PathVariable String userId) {
        userService.updateUser(userId, updatedUser);
        return "redirect:/users/list";
    }
}
