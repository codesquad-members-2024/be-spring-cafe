package codesquad.springcafe.controller;

import codesquad.springcafe.dto.UpdateUser;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.service.UserService;
import codesquad.springcafe.service.validator.UserValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @GetMapping("/user/register")
    public String showForm() {
        return "user/register/form";
    }

    @PostMapping("/user/register")
    public String register(User user, Model model) {
        try {
            userService.addNewUser(user);
        } catch (DuplicateKeyException e) {
            model.addAttribute("error", true);
            return "user/register/form";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String showList(Model model, HttpSession session) {
        Object value = session.getAttribute("sessionUser");
        User actual = (User) value;
        model.addAttribute("loggedInUserId", actual.getUserId());
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/user/profile/{userId}")
    public String showProfile(Model model, @PathVariable("userId") String userId) {
        model.addAttribute("user", userService.getUserById(userId));
        return "user/profile";
    }

    @GetMapping("/user/profile/{userId}/update")
    public String showEditProfileForm(Model model, @PathVariable("userId") String userId, HttpSession session) {
        User actual = (User) session.getAttribute("sessionUser");
        User expected = userService.getUserById(userId);
        userValidator.validSameUser(expected, actual);

        model.addAttribute("user", userId);
        return "user/updateForm";
    }

    @PutMapping("/user/profile/{userId}/update")
    public String editProfile(UpdateUser updateUser, @PathVariable("userId") String userId, Model model, HttpSession session) {
        User actual = (User) session.getAttribute("sessionUser");
        User expected = userService.getUserById(userId);
        userValidator.validSameUser(expected, actual);

        if (userService.editUserProfile(updateUser, actual)) {
            return "redirect:/user/list";
        } else {
            model.addAttribute("error", true);
            model.addAttribute("userEdit", updateUser);
            return "user/updateForm";
        }
    }
}
