package codesquad.springcafe.domain.user.controller;

import codesquad.springcafe.domain.user.data.UserData;
import codesquad.springcafe.domain.user.data.UserJoinData;
import codesquad.springcafe.domain.user.data.UserListData;
import codesquad.springcafe.domain.user.data.UserLoginData;
import codesquad.springcafe.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입
    @PostMapping("/user")
    public String join(@Valid @ModelAttribute UserJoinData userJoinData, Model model,
                       HttpSession httpSession) {
        Long userId = userService.join(userJoinData);

        httpSession.setAttribute("userId", userId);
        httpSession.setMaxInactiveInterval(3600);

        model.addAttribute("user", userJoinData);

        return "/user/registration_success";
    }

    @PostMapping("/user/login")
    public String login(@Valid @ModelAttribute UserLoginData userLoginData,
                        HttpSession httpSession) {
        Long userId = userService.login(userLoginData);
        httpSession.setAttribute("userId", userId);
        httpSession.setMaxInactiveInterval(3600);

        return "redirect:/";
    }

    // 회원 목록 조회
    @GetMapping("/users")
    public String getUsers(Model model) {
        UserListData userListData = userService.getUsers();

        model.addAttribute("totalUserCnt", userListData.getTotalUserCnt());
        model.addAttribute("users", userListData.getUserList());

        return "user/list";
    }

    // 회원 상세 조회 (프로필 조회)
    @GetMapping("/profile/{userId}")
    public String getUser(@PathVariable(name = "userId") Long userId, Model model) {
        UserData userData = userService.getUser(userId);

        model.addAttribute("user", userData);

        return "user/profile";
    }
}
