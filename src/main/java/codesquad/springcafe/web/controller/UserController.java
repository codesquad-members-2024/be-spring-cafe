package codesquad.springcafe.web.controller;

import codesquad.springcafe.web.domain.User;
import codesquad.springcafe.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}
