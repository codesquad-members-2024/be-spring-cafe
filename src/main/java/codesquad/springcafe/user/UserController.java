package codesquad.springcafe.user;

import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.User;
import codesquad.springcafe.user.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userRepository.storeUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.getAllUsers());
        return "/user/list";
    }

    @GetMapping("/profile/{name}")
    public String showUserProfile(Model model, @PathVariable String name) {
        User user;
        try {
            user = userRepository.findByName(name);
        } catch (NoSuchUserException noUser) {
            return "redirect:/user/no_user.html";
        }

        model.addAttribute("user", user);

        return "user/profile";
    }
}
