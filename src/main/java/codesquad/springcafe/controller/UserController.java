package codesquad.springcafe.controller;

import codesquad.springcafe.dto.UserProfileDto;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.exception.UnauthorizedAccessException;
import codesquad.springcafe.model.UpdateUser;
import codesquad.springcafe.model.User;
import codesquad.springcafe.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    public static final String LOGIN_USER_ID = "loginUserId";
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
    public String getLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String userId,
            @RequestParam String password,
            @RequestParam(defaultValue = "/") String redirectUri,
            HttpSession httpSession) {

        if (!userService.isPasswordMatch(userId, password)) {
            return "redirect:/users/login";
        }
        httpSession.setAttribute(LOGIN_USER_ID, userId);
        log.debug("로그인 성공: {}", userId);
        return "redirect:" + redirectUri;
    }

    @GetMapping("/join")
    public String getSignUpForm() {
        return "user/form";
    }

    @GetMapping("update/{userId}")
    public String getUserUpdateForm(@PathVariable String userId, Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute(LOGIN_USER_ID);
        checkAuthority(userId, loginUserId);

        model.addAttribute("userId", userId);
        return "user/updateForm";
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable String userId, @ModelAttribute UserUpdateDto userUpdateDto, HttpSession session) {
        String loginUserId = (String) session.getAttribute(LOGIN_USER_ID);
        checkAuthority(userId, loginUserId);

        if (!userService.isPasswordMatch(userId, userUpdateDto.getPassword())) {
            return "redirect:/users/" + userId + "/form";
        }
        UpdateUser updateUser = userUpdateDto.toEntity(userId);
        userService.update(updateUser);

        log.debug("user {} update", loginUserId);
        return "redirect:/users";
    }

    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

    public void checkAuthority(String userId, String loginUserId) {
        if (!userId.equals(loginUserId)) {
            throw new UnauthorizedAccessException("접근 권한이 없습니다.");
        }
    }

}
