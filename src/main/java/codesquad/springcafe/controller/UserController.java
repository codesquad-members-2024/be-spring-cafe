package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = getLogger(UserController.class);
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public String createUser(@RequestParam("userId") String id,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email) {

        // 회원가입
        User user = new User(id, password, name, email);
        userRepository.addUser(user);
        log.info(user.toString());
        return "redirect:/user/users";
    }
    @GetMapping("/form")
    public String userForm() {
        return "user/form";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users" , userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }
