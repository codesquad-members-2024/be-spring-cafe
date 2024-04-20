package codesquad.springcafe.controller;

import codesquad.springcafe.dto.UpdateUser;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/register")
    public String showForm() {
        return "user/register/form";
    }

    @PostMapping("/user/register")
    public String register(User user) {
        userService.addNewUser(user);
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
    public String showEditProfileForm(Model model, @PathVariable("userId") String userId) {
        model.addAttribute("user", userId);
        return "user/updateForm";
    }

    @PutMapping("/user/profile/{userId}/update")
    public String editProfile(UpdateUser updateUser, @PathVariable("userId") String userId, Model model,
                              HttpSession session) {
        Object value = session.getAttribute("sessionUser");
        User actual = (User) value;
        User expected = userService.getUserById(userId);

        if (userService.editUserProfile(updateUser, expected, actual)) {
            return "redirect:/user/list";
        } else {
            model.addAttribute("error", true);
            model.addAttribute("userEdit", updateUser);
            return "user/updateForm";
        }
    }
}
