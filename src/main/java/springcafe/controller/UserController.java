package springcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springcafe.dto.UserCreateForm;
import springcafe.dto.UserDto;
import springcafe.model.User;
import springcafe.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signUp() {
        return "user/form";
    }

    @GetMapping("list")
    public String userList(Model model) {
        List<UserDto> userDto = userService.findAll();
        model.addAttribute("userList", userDto);
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable String userId, Model model) {

        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/signup")
    public String signUp(UserCreateForm userCreateForm) {
        userService.create(userCreateForm.getUserId(), userCreateForm.getPassword(), userCreateForm.getName(),
                userCreateForm.getEmail());

        return "redirect:/user/list";
    }


}
