package codesquad.springcafe.controller;

import codesquad.springcafe.database.user.UserDatabase;
import codesquad.springcafe.form.user.LoginForm;
import codesquad.springcafe.model.User;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    public static final String LOGIN_SESSION_NAME = "loginUser";
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserDatabase userDatabase;

    public LoginController(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    /**
     * 사용자에게 로그인 폼을 보여줍니다.
     */
    @GetMapping("/login")
    public String loginForm(Model model) {
        LoginForm loginForm = new LoginForm("", "");
        model.addAttribute("loginForm", loginForm);
        return "user/login";
    }

    /**
     * 사용자가 작성한 로그인폼을 받아 이메일과 비밀번호 모두 일치하는 User를 찾으면 세션에 입력하고 사용자가 원하는 경로로 리다이렉트합니다.
     */
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        HttpSession session, @RequestParam(defaultValue = "/") String redirectUrl) {
        Optional<User> optionalUser = userDatabase.findByEmail(loginForm.getEmail());
        validateLoginInfo(loginForm.getPassword(), bindingResult, optionalUser);

        if (bindingResult.hasErrors()) {
            logger.error("errors={}", bindingResult);
            return "user/login";
        }
        User loginUser = optionalUser.get();

        session.setAttribute(LOGIN_SESSION_NAME, loginUser);
        logger.info("{} 님이 로그인하셨습니다", loginUser.getNickname());
        return "redirect:" + redirectUrl;
    }

    /**
     * 사용자가 로그아웃을 시도하면 세션정보를 만료시킵니다.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    private void validateLoginInfo(String password, BindingResult bindingResult, Optional<User> optionalUser) {
        if (optionalUser.isEmpty() || isWrongPassword(password, optionalUser.get())) {
            bindingResult.reject("Wrong");
        }
    }

    private boolean isWrongPassword(String password, User user) {
        return !user.hasSamePassword(password);
    }
}
