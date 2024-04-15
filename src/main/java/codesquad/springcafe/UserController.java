package codesquad.springcafe;


import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/add")
    public String userForm() {
        return "user/form";
    }

    @PostMapping("/user/add")
    public String create(@ModelAttribute User user) {
        userRepository.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        List<User> users = userRepository.users();
        model.addAttribute(users);
        return "user/list";
    }

    @GetMapping("/user/{userId}")
    public String userProfile(@PathVariable String userId, Model model) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute(user);
            return "/user/profile";
        }
        return null;
    }
}