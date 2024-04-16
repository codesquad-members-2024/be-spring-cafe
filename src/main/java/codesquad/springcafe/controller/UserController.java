package codesquad.springcafe.controller;

import codesquad.springcafe.dto.UserJoinDto;
import codesquad.springcafe.dto.UserLoginDto;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.exception.service.DuplicateUserIdException;
import codesquad.springcafe.model.LoginUser;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.model.UpdatedUser;
import codesquad.springcafe.model.User;
import codesquad.springcafe.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final String REQUEST_URL_REGEX = ".*requestedUrl=([^&]+).*";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/join")
    public String showJoinPage() {
        return "user/join";
    }

    @PostMapping("/join")
    public String processJoinForm(@Valid @ModelAttribute UserJoinDto userJoinDto) {
        User user = userJoinDto.creatUser();
        try {
            userService.addUser(user);
            return "redirect:/users/join/success";
        } catch (DuplicateUserIdException e) {
            return "redirect:/users/join?error";
        }
    }

    @GetMapping("/join/success")
    public String showSuccessJoinPage() {
        return "user/join_success";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model, HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (queryString != null && queryString.matches(REQUEST_URL_REGEX)) { // 권한이 없어 로그인 페이지로 리다이렉트된 것이라면
            model.addAttribute("redirectFlag", "푸카페 회원만 접근 가능한 페이지입니다.");
        } else {
            model.addAttribute("redirectFlag", "로그인");
        }
        return "user/login";
    }

    @PostMapping("/login")
    public String processLoginForm(@Valid @ModelAttribute UserLoginDto userLoginDto, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        LoginUser loginUser = userLoginDto.creatLoginUser();

        if (userService.isJoinedUser(loginUser)) {
            String userId = loginUser.getUserId();
            Optional<SessionUser> sessionUser = userService.findSessionUserById(userId);
            if (sessionUser.isPresent()) {
                httpSession.setAttribute("sessionUser", sessionUser.get());
                httpSession.setMaxInactiveInterval(3600);
                String redirectUrl = getRedirectUrlFromQueryString(request.getQueryString());
                if (redirectUrl != null) {
                    return "redirect:" + redirectUrl;
                }
                return "redirect:/"; // 저장된 URL이 없을 경우, 홈 페이지로 리다이렉션한다.
            }
        }
        return "redirect:/users/login?error"; // 로그인이 실패할 경우, 에러 메시지를 출력한다.
    }

    private String getRedirectUrlFromQueryString(String queryString) {
        if (queryString != null && queryString.matches(REQUEST_URL_REGEX)) {
            return queryString.replaceAll(REQUEST_URL_REGEX, "$1");
        }
        return null;
    }

    @GetMapping("/logout")
    public String processLogoutForm(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/list")
    public String showListPage(Model model) {
        List<User> users = userService.findAllUser();
        model.addAttribute("userList", users);
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String showProfilePage(Model model, @PathVariable String userId) {
        Optional<User> user = userService.findUserByUserId(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/profile";
        }
        return "redirect:/error/not-found";
    }

    @GetMapping("/match-pw/{userId}")
    public String showMatchPasswordPage(Model model, @PathVariable String userId) {
        model.addAttribute("userId", userId);
        return "user/match_pw";
    }

    @PostMapping("/match-pw/{userId}")
    public String processMatchPasswordForm(@PathVariable String userId, @RequestParam String userPassword,
                                           HttpSession httpSession) {
        Optional<User> user = userService.findUserByUserId(userId);
        if (user.isPresent()) {
            if (user.get().matchPassword(userPassword)) { // 비밀번호 일치 성공
                httpSession.setAttribute("matchPw", true);
                return "redirect:/users/update/{userId}";
            }
        }
        return "redirect:/users/match-pw/{userId}?error";
    }

    @GetMapping("/update/{userId}")
    public String showUpdatePage(Model model, @PathVariable String userId, HttpSession httpSession) {
        Object matchedPwFlag = httpSession.getAttribute("matchPw");
        if (matchedPwFlag != null && (boolean) matchedPwFlag) { // 비밀번호 일치 여부 검증을 끝냈다면
            model.addAttribute("userId", userId);
            return "user/update";
        }
        return "redirect:/users/match-pw/{userId}";
    }

    @PutMapping("/update/{userId}")
    public String updateUserInfo(@ModelAttribute UserUpdateDto userUpdateDto, @PathVariable String userId) {
        UpdatedUser updatedUser = userUpdateDto.creatUpdateUser();
        userService.updateUser(userId, updatedUser);
        return "redirect:/users/list";
    }

    @GetMapping("/invalid-modify")
    public String showInvalidModifyPage() {
        return "error/cannot_modify_other_users";
    }
}
