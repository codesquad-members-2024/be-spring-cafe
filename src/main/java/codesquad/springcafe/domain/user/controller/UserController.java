package codesquad.springcafe.domain.user.controller;

import codesquad.springcafe.domain.user.data.UserResponse;
import codesquad.springcafe.domain.user.data.UserJoinRequest;
import codesquad.springcafe.domain.user.data.UserListResponse;
import codesquad.springcafe.domain.user.data.UserLoginRequest;
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
    public String join(@Valid @ModelAttribute UserJoinRequest userJoinRequest, Model model,
                       HttpSession httpSession) {
        Long userId = userService.join(userJoinRequest);

        httpSession.setAttribute("userId", userId);
        httpSession.setAttribute("isLoggedIn", true);
        httpSession.setMaxInactiveInterval(30);

        model.addAttribute("user", userJoinRequest);

        return "/user/registration_success";
    }

    // 로그인
    @PostMapping("/user/login")
    public String login(@Valid @ModelAttribute UserLoginRequest userLoginRequest,
                        HttpSession httpSession) {
        Long userId = userService.login(userLoginRequest);
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
        UserListResponse userListResponse = userService.getUsers();

        model.addAttribute("totalUserCnt", userListResponse.getTotalUserCnt());
        model.addAttribute("users", userListResponse.getUserList());

        return "user/list";
    }

    // 회원 상세 조회 (프로필 조회)
    @GetMapping("/profile/{loginId}")
    public String getUser(@PathVariable(name = "loginId") String loginId, Model model) {
        UserResponse userResponse = userService.getUser(loginId);

        model.addAttribute("user", userResponse);

        return "user/profile";
    }

    // 마이페이지 조회
    @GetMapping("/profile/my")
    public String getMyProfile(HttpSession httpSession, Model model) {
        Object userId = httpSession.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("인증이 필요한 요청입니다.");
        }
        UserResponse userResponse = userService.getMyProfile((Long) userId);

        model.addAttribute("user", userResponse);
        return "user/profile";
    }
}
