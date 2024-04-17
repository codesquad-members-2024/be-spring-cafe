package codesquad.springcafe.user;

import codesquad.springcafe.user.dto.UserSigninDto;
import codesquad.springcafe.user.dto.UserSignupDto;
import codesquad.springcafe.user.dto.UserUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 등록 페이지
    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    // 유저 등록 메서드
    @PostMapping("/create")
    public String register(UserSignupDto userSignupDto) {
        userService.save(userSignupDto);
        return "redirect:/";
    }

    // 유저 목록 페이지
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    // 유저 프로필 페이지
    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findByUserId(userId));
        return "user/profile";
    }

    // 유저 정보 수정 페이지
    @GetMapping("{userId}/form")
    public String updateProfilePage(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findByUserId(userId));
        return "user/update_form";
    }


    //수정된 회원 정보 post
    @PutMapping("{userId}/form")
    public String putUpdateProfile(@PathVariable String userId, UserUpdateDto userUpdateDto) {
        userService.updateUserProfile(userUpdateDto, userId);
        return "redirect:/user";
    }

    //유저 로그인 페이지
    @GetMapping("/login")
    public String userLoginForm() {
        return "user/login";
    }

    //유저 로그인
    @PostMapping("/login")
    public String userLogin(UserSigninDto userSigninDto, HttpServletRequest httpServletRequest,
        @RequestParam(value = "return_to", defaultValue = "/") String returnUrl) {
        User signInUser = userService.userLogin(userSigninDto);

        if (signInUser == null) {
            log.info("로그인 실패 - 사용자 아이디: {}", userSigninDto.getUserId());
            return "redirect:/user/login_failed";
        }

        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("userId", signInUser.getUserId());
        httpSession.setAttribute("nickname", signInUser.getNickname());
        log.info("로그인 성공 - 사용자 아이디: {}", userSigninDto.getUserId());

        return "redirect:" + returnUrl;
    }

    //유저 로그아웃
    @GetMapping("/logout")
    public String userLogout(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.invalidate();
        return "redirect:/";
    }

    //로그인 실패 페이지
    @GetMapping("/login_failed")
    public String loginFailed() {
        log.info("로그인 실패 페이지로 이동");
        return "user/login_failed";
    }
}
