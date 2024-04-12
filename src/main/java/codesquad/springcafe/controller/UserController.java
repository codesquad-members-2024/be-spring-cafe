package codesquad.springcafe.controller;

import codesquad.springcafe.dto.UserProfileDto;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.model.User;
import codesquad.springcafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getUserList(Model model) {
        log.debug("user list");
        List<UserProfileDto> userProfileDtos = userService.findAllUsers().stream()
                .map(UserProfileDto::toDto)
                .toList();
        model.addAttribute("users", userProfileDtos);
        return "user/list";
    }

    @PostMapping
    public String register(@ModelAttribute User user) {
        log.debug("register");
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String getUser(@PathVariable String userId, Model model) {
        log.debug("get User : {}", userId);
        User user = userService.findUserById(userId);
        model.addAttribute("user", UserProfileDto.toDto(user));
        return "user/profile";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/join")
    public String getSignUpForm() {
        return "/user/form";
    }

    @GetMapping("/{userId}/form")
    public String getUserUpdateForm(@PathVariable String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/updateForm";
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable String userId, @RequestParam("password") String password, @RequestParam("newPassword") String newPassword, @RequestParam("name") String name, @RequestParam("email") String email) {
        UserUpdateDto userUpdateDto = new UserUpdateDto(userId, password, newPassword, name, email);
        try {
            userService.update(userUpdateDto);
        } catch (IllegalArgumentException e) {
            log.debug("user {} password does not match", userUpdateDto.getUserId());
            return "redirect:/users/" + userId + "/form";
        }
        log.debug("user {} update", userUpdateDto.getUserId());
        return "redirect:/users";
    }
}
