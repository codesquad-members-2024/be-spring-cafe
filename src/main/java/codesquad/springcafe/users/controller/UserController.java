package codesquad.springcafe.users.controller;

import codesquad.springcafe.exception.PasswordMismatchException;
import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.model.user.dto.*;
import codesquad.springcafe.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/login")
    public String showLoginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(HttpServletRequest request, UserLoginDto userLoginDto) {
        HttpSession session = request.getSession();
        try {
            UserPreviewDto userPreviewDto = userService.loginUser(userLoginDto);
            session.setAttribute("sessionedUser", userPreviewDto);
        } catch (UserNotFoundException | PasswordMismatchException e) {
            logger.error(e.getMessage());
            return "redirect:/users/login";
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

}
