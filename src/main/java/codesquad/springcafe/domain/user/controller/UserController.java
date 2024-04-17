package codesquad.springcafe.domain.user.controller;

import codesquad.springcafe.domain.user.data.*;
import codesquad.springcafe.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입
    @PostMapping("/user")
    public String join(@Valid @ModelAttribute UserJoinRequest userJoinRequest,
                       HttpSession httpSession) {
        Long userId = userService.join(userJoinRequest);
        setSession(httpSession, userId);

        return "redirect:/welcome";
    }

    // 환영 페이지 (현재 로그인한 유저에 대한 정보 표시)
    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        Long userId = getSessionUserId(session);
        UserResponse myProfile = userService.getMyProfile(userId);

        model.addAttribute("user", myProfile);

        return "/user/registration_success";
    }

    // 로그인
    @PostMapping("/user/login")
    public String login(@Valid @ModelAttribute UserLoginRequest userLoginRequest,
                        HttpSession httpSession) {
        Long userId = userService.login(userLoginRequest);
        setSession(httpSession, userId);

        return "redirect:/";
    }

    // 로그아웃
    @PostMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Object userId = session.getAttribute("userId");
        if (!userService.logout(request.getParameter("userId"), userId)) {
            throw new IllegalStateException("로그아웃 할 수 없습니다.");  // TODO : exception 추가
        }

        session.removeAttribute("userId");
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
        Long userId = getSessionUserId(httpSession);
        UserResponse userResponse = userService.getMyProfile(userId);

        model.addAttribute("user", userResponse);
        model.addAttribute("my", true); // 마이페이지에서만 버튼 보기 가능
        return "user/profile";
    }

    // 내 프로필 수정 페이지 접근
    @GetMapping("/profile/my/edit")
    public String getProfileEditForm(HttpSession httpSession,
                                     Model model) {
        Long userId = getSessionUserId(httpSession);

        UserResponse userInfo = userService.getMyProfile(userId);
        model.addAttribute("user", userInfo);

        return "/user/edit_form";
    }

    // 내 프로필 수정 (이름, 이메일만 수정 가능)
    @PutMapping("/profile/my/edit")
    public String updateMyProfile(HttpSession httpSession,
                                 @Valid @ModelAttribute UserUpdateRequest userUpdateRequest) {
        Long userId = getSessionUserId(httpSession);

        userService.updateMyProfile(userId, userUpdateRequest);

        return "redirect:/profile/my";
    }

    private void setSession(HttpSession httpSession, Long userId) {
        httpSession.setAttribute("userId", userId);
        httpSession.setAttribute("CSRF_TOKEN", UUID.randomUUID());
        httpSession.setMaxInactiveInterval(3600);
    }

    private Long getSessionUserId(HttpSession httpSession) {
        Object userId = httpSession.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("인증이 필요한 요청입니다.");
        }
        return (Long) userId;
    }
}
