package codesquad.springcafe.users.controller;

import codesquad.springcafe.exception.PasswordMismatchException;
import codesquad.springcafe.users.service.UserService;
import model.user.dto.UserCreateDto;
import model.user.dto.UserPreviewDto;
import model.user.dto.UserUpdateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(Model model) {
        ArrayList<UserPreviewDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("totalUsers", users.size());

        return "user/list";
    }

    @PostMapping
    public String registerUser(UserCreateDto userCreateDto, Model model) {
        userService.createUser(userCreateDto);

        model.addAttribute("userEmail", userCreateDto.getEmail());
        model.addAttribute("userId", userCreateDto.getUserId());
        model.addAttribute("userName", userCreateDto.getName());
        return "user/login_success";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        UserPreviewDto userPreviewDto = userService.findUserById(userId);

        model.addAttribute("user", userPreviewDto);

        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String getUserEditPage(@PathVariable String userId, Model model) {
        UserPreviewDto userPreviewDto = userService.findUserById(userId);

        model.addAttribute("user", userPreviewDto);

        return "user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, UserUpdateData updateData, Model model) {
        try {
            userService.updateUser(userId, updateData);
        } catch (PasswordMismatchException e) {
            logger.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            UserPreviewDto userPreviewDto = userService.findUserById(userId);
            model.addAttribute("user", userPreviewDto);
            model.addAttribute("error", true);
            return "user/updateForm";
        }
        return "redirect:/users";
    }

}
