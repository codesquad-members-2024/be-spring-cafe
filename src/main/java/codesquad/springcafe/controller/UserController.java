package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserCreateDto;
import codesquad.springcafe.dto.UserLoginDto;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.error.exception.AccessDeniedException;
import codesquad.springcafe.error.exception.UserNotFoundException;
import codesquad.springcafe.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/create")
    public String createUserForm() {
        return "user/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserCreateDto userCreateDto) {
        User user = new User(userCreateDto);
        userService.createUser(user);

        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable String userId, Model model) {
        User user = userService.findByUserId(userId);
        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/{userId}/update")
    public String updateUserForm(@PathVariable String userId, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("user");

        if (loginUser == null) {
            throw new UserNotFoundException("로그인 사용자가 존재하지 않습니다.");
        }

        // 사용자 ID와 요청 파라미터의 ID가 다르면 권한x
        if (!loginUser.matchUserId(userId)) {
            throw new AccessDeniedException("프로필 수정에 대한 권한이 없습니다.");
        }

        model.addAttribute("user", loginUser);

        return "user/update";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, @ModelAttribute UserUpdateDto userUpdateDto,
                             Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("user");

        try {
            userService.updateUser(loginUser, userUpdateDto);
        } catch (UserNotFoundException | AccessDeniedException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/update";
        }

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto userLoginDto,
                        Model model, HttpSession session) {
        try {
            User user = userService.loginUser(userLoginDto);
            session.setAttribute("user", user);
        } catch (UserNotFoundException | AccessDeniedException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";
        }

        // 비로그인 상태에서 저장한 redirectUri이 있으면
        String redirectUri = (String) session.getAttribute("redirectUri");
        if (redirectUri != null) {
            session.removeAttribute("redirectUri");
            return "redirect:" + redirectUri;
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }
}