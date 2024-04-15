package codesquad.springcafe.web.controller;

import codesquad.springcafe.web.domain.User;
import codesquad.springcafe.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/new")
    public String createForm() {
        return "user/form";
    }

//    @PostMapping("/user/new")
//    public String createUser(UserForm form) {
//        User user = new User();
//        user.setUserId(form.getUserId());
//        user.setName(form.getName());
//        user.setPassword(form.getPassword());
//        user.setEmail(form.getEmail());
//
//        userService.join(user);
//
//        return "redirect:/user/list";
//    }

    @PostMapping("/user/new")
    public String createUser(@ModelAttribute User user) {

        userService.join(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String userList(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/profile/{userName}")
    public String userProfile(@PathVariable String userName, Model model) {
        System.out.println("userName = " + userName);
        Optional<User> user = userService.findUser(userName);
        user.ifPresent(u -> model.addAttribute("user", u));

        return "user/profile";
    }

}
