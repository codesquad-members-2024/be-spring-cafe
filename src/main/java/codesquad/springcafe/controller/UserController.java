package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/create")
    public String join() {
        return "/user/form";
    }

    @PostMapping("/users/create")
    public String join(@ModelAttribute User user) {
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String userDetails(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findOne(userId));
        return "/user/profile";
    }

    @GetMapping("/users/{userId}/update")
    public String userUpdate(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findOne(userId));
        return "/user/updateForm";
    }

    @PutMapping("/users/{userId}/update")
    public String userUpdate(@PathVariable String userId, @ModelAttribute UserUpdateDto userUpdateDto) {
        userService.updateUser(userId, userUpdateDto);
        return "redirect:/users";
    }
}
