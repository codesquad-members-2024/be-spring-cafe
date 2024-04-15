package codesquad.springcafe.controller;

import codesquad.springcafe.DB.Database;
import codesquad.springcafe.DB.H2Database;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.UserEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final H2Database h2Database;

    @Autowired
    public UserController(H2Database h2Database) {
        this.h2Database = h2Database;
    }

    @GetMapping("/user/register")
    public String showForm() {
        return "user/register/form";
    }

    @PostMapping("/user/register")
    public String register(User user) {
        h2Database.addUser(user);
        logger.debug("new user: " + user.toString());
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String showList(Model model) {
        model.addAttribute("users", h2Database.getAllUsers());
        return "user/list";
    }

    @GetMapping("/user/profile/{userId}")
    public String showProfile(Model model, @PathVariable("userId") String userId) {
        model.addAttribute("user", h2Database.getUser(userId));
        return "user/profile";
    }

    @GetMapping("/user/profile/{userId}/update")
    public String showEditProfileForm(Model model, @PathVariable("userId") String userId) {
        model.addAttribute("user", userId);
        return "user/updateForm";
    }

    @PutMapping("/user/profile/{userId}/update")
    public String editProfile(UserEdit userEdit, @PathVariable("userId") String userId, Model model) {
        User target = h2Database.getUser(userId);
        String password = userEdit.getPassword();

        if (target.checkPassword(password)) {
            String newPassword = userEdit.getNewPassword();
            String newName = userEdit.getName();
            String newEmail = userEdit.getEmail();
            h2Database.putUser(newPassword, newName, newEmail, userId);
            return "redirect:/user/list";
        } else {
            model.addAttribute("error", true);
            model.addAttribute("userEdit", userEdit);
            return "user/updateForm";
        }
    }
}
