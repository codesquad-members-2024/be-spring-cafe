package codesquad.springcafe.domain.user.controller;

import codesquad.springcafe.domain.user.data.*;
import codesquad.springcafe.domain.user.service.UserService;
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
        UserCredentials userCredentials = userService.join(userJoinRequest);
        setSession(httpSession, userCredentials);

        return "redirect:/welcome";
    }

    // 환영 페이지 (현재 로그인한 유저에 대한 정보 표시)
    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        Long userId = getUserCredentials(session).getUserId();
        UserResponse myProfile = userService.getMyProfile(userId);

        model.addAttribute("user", myProfile);

        return "user/registration_success";
    }

    // 로그인
    @PostMapping("/user/login")
    public String login(@Valid @ModelAttribute UserLoginRequest userLoginRequest,
                        HttpSession httpSession) {
        UserCredentials userCredentials = userService.login(userLoginRequest);
        setSession(httpSession, userCredentials);

        return "redirect:/";
    }

    // 로그아웃
    @PostMapping("/user/logout")
    public String logout(HttpSession httpSession) {
        Object sessionUserId = httpSession.getAttribute("userId");
        if (!userService.logout(sessionUserId)) {
            throw new IllegalStateException("로그아웃 할 수 없습니다.");  // TODO : exception 추가
        }

        httpSession.invalidate();
        return "redirect:/";
    }

    // 회원 탈퇴 페이지 접근
    @GetMapping("/user/withdraw/{loginId}")
    public String getWithdrawForm(HttpSession httpSession,
                                  @PathVariable("loginId") String loginId,
                                  @RequestHeader("referer") String referer,
                                  Model model) {
        Long sessionUserId = getUserCredentials(httpSession).getUserId();

        userService.getWithdrawForm(loginId, sessionUserId);
        model.addAttribute("loginId", loginId);
        model.addAttribute("goBack", referer);

        return "user/delete";
    }

    // 회원 탈퇴
    @DeleteMapping("/user/withdraw/{loginId}")
    public String withdraw(HttpSession httpSession,
                           @PathVariable("loginId") String userId) {

        Long sessionUserId = getUserCredentials(httpSession).getUserId();
        if(!userService.withdraw(userId, sessionUserId)){
            throw new IllegalStateException("탈퇴할 수 없습니다.");
        }

        httpSession.invalidate();
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
        Long userId = getUserCredentials(httpSession).getUserId();
        UserResponse userResponse = userService.getMyProfile(userId);

        model.addAttribute("user", userResponse);
        model.addAttribute("my", true); // 마이페이지에서만 버튼 보기 가능
        return "user/profile";
    }

    // 내 프로필 수정 페이지 접근
    @GetMapping("/profile/my/edit")
    public String getProfileEditForm(HttpSession httpSession,
                                     Model model) {
        Long userId = getUserCredentials(httpSession).getUserId();

        UserResponse userInfo = userService.getMyProfile(userId);
        model.addAttribute("user", userInfo);

        return "user/edit_form";
    }

    // 내 프로필 수정 (이름, 이메일만 수정 가능)
    @PutMapping("/profile/my/edit")
    public String updateMyProfile(HttpSession httpSession,
                                 @Valid @ModelAttribute UserUpdateRequest userUpdateRequest) {
        Long userId = getUserCredentials(httpSession).getUserId();

        userService.updateMyProfile(userId, userUpdateRequest);

        return "redirect:/profile/my";
    }

    private void setSession(HttpSession httpSession, UserCredentials userCredentials) {
        httpSession.setAttribute("userCredentials", userCredentials);
        httpSession.setMaxInactiveInterval(3600);
    }

    private UserCredentials getUserCredentials(HttpSession httpSession) {
        Object userCredentials = httpSession.getAttribute("userCredentials");
        if (userCredentials == null) {
            throw new IllegalStateException("인증이 필요한 요청입니다.");
        }
        return (UserCredentials) userCredentials;
    }
}
