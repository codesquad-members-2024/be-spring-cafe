package codesquad.springcafe.web.controller;

import codesquad.springcafe.service.AuthService;
import codesquad.springcafe.web.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    /**
     * 1. 로그인을 진행하면 해당 유저가 존재하는지 확인
     * 2. 해당 유저가 존재한다면, 비밀번호가 일치하는지 확인
     * 3. 세션 ID 생성 후, 세션 저장소에 보관
     * @param loginDto
     * @param request
     * @return
     */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, HttpServletRequest request) {
        if (authService.isValidUser(loginDto)) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", authService.findOne(loginDto.getUserId()));
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
