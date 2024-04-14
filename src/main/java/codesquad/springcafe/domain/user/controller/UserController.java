package codesquad.springcafe.domain.user.controller;

import codesquad.springcafe.domain.user.data.UserData;
import codesquad.springcafe.domain.user.data.UserJoinData;
import codesquad.springcafe.domain.user.data.UserListData;
import codesquad.springcafe.domain.user.data.UserLoginData;
import codesquad.springcafe.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
        httpSession.setAttribute("isLoggedIn", true);
        httpSession.setMaxInactiveInterval(30);

        model.addAttribute("user", userJoinData);

        return "/user/registration_success";
    }

    // 로그인
    @PostMapping("/user/login")
    public String login(@Valid @ModelAttribute UserLoginData userLoginData,
                        HttpSession httpSession) {
        Long userId = userService.login(userLoginData);
        httpSession.setAttribute("userId", userId);
        httpSession.setAttribute("isLoggedIn", true);
        httpSession.setMaxInactiveInterval(3600);

        return "redirect:/";
    }

    // 로그아웃
    @PostMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Object userId = session.getAttribute("userId");
        userService.logout(request.getParameter("userId"), userId);

        session.removeAttribute("userId");
        session.removeAttribute("isLoggedIn");
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
    @GetMapping("/profile/{loginId}")
    public String getUser(@PathVariable(name = "loginId") String loginId, Model model) {
        UserData userData = userService.getUser(loginId);

        model.addAttribute("user", userData);

        return "user/profile";
    }
}
