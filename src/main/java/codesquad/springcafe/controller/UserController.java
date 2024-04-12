package codesquad.springcafe.controller;

import codesquad.springcafe.DB.Database;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.UserEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/form.html")
    public String showForm() {
        return "user/form";
    }

    @PostMapping("/user")
    public String register(User user) {
        Database.addUser(user);
        logger.debug("new user: " + user.toString());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showList(Model model) {
        model.addAttribute("users", Database.getAllUsers());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(Model model, @PathVariable("userId") String userId) {
        model.addAttribute("user", Database.getUser(userId));
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String showEditProfileForm(Model model, @PathVariable("userId") String userId) {
        model.addAttribute("user", userId);
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/form")
    public String editProfile(UserEdit userEdit, @PathVariable("userId") String userId, Model model) {
        User target = Database.getUser(userId);
        String password = userEdit.getPassword();

        if (target.checkPassword(password)) {
            String newPassword = userEdit.getNewPassword();
            String newName = userEdit.getName();
            String newEmail = userEdit.getEmail();
            target.edit(newPassword, newName, newEmail);
            return "redirect:/users";
        } else {
            model.addAttribute("error",true);
            model.addAttribute("userEdit",userEdit);
            return "user/updateForm";
        }
    }
}
