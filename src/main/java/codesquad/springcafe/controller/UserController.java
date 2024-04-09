package codesquad.springcafe.controller;

import codesquad.springcafe.dto.User;
import codesquad.springcafe.repository.MemoryUserRepository;
import codesquad.springcafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users") // default path
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String users(Model model){
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users); // users 를 "users" 라는 이름으로 등록 => DB 에서 가져온 데이터를 users 라는 이름으로 뷰 페이지에서 사용 가능
        return "users/list";
    }

    @GetMapping("/signup")
    public String newUserForm(){
        return "users/form";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute  User user){
        userService.signup(user);

        return "redirect:/users";
    }
}
