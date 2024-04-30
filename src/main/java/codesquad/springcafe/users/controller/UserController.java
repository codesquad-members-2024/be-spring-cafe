package codesquad.springcafe.users.controller;

import codesquad.springcafe.exception.PasswordMismatchException;
import codesquad.springcafe.exception.UserAccessException;
import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.users.model.dto.*;
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
    public String registerUser(UserCreationRequest userCreationRequest, Model model) {
        userService.createUser(userCreationRequest);

        model.addAttribute("userEmail", userCreationRequest.getEmail());
        model.addAttribute("userId", userCreationRequest.getUserId());
        model.addAttribute("userName", userCreationRequest.getName());
        return "user/login_success";
    }

    @GetMapping("/profile/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        UserPreviewDto userPreviewDto = userService.findUserById(userId);

        model.addAttribute("user", userPreviewDto);

        return "user/profile";
    }

    @GetMapping("/update/{userId}")
    public String getUserEditPage(@PathVariable String userId, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        UserPreviewDto sessionedUser = (UserPreviewDto) session.getAttribute("sessionedUser");

        if (sessionedUser.getUserId().equals(userId)) {

            UserPreviewDto userPreviewDto = userService.findUserById(userId);

            model.addAttribute("user", userPreviewDto);

            return "user/updateForm";
        }
        if (sessionedUser == null) {
            throw new  UserNotFoundException("존재하지 않는 사용자입니다.");
        }
        throw new UserAccessException("수정할 수 있는 권한이 없습니다.");
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable String userId, UserUpdateRequest updateData, Model model) {
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
    public String loginUser(HttpServletRequest request, UserLoginRequest userLoginRequest, Model model) {
        HttpSession session = request.getSession();
        try {
            UserPreviewDto userPreviewDto = userService.loginUser(userLoginRequest);
            session.setAttribute("sessionedUser", userPreviewDto);
        } catch (UserNotFoundException | PasswordMismatchException e) {
            model.addAttribute("errorMsg", e.getMessage());
            logger.error(e.getMessage());
            return "user/login";
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

}
