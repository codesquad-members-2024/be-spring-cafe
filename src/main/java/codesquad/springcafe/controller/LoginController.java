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

@Controller
public class LoginController {
    public static final String LOGIN_SESSION_NAME = "loginUser";
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserDatabase userDatabase;

    public LoginController(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        LoginForm loginForm = new LoginForm("", "");
        model.addAttribute("loginForm", loginForm);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        HttpSession session) {
        Optional<User> optionalUser = userDatabase.findByEmail(loginForm.getEmail());
        validateLoginInfo(loginForm.getPassword(), bindingResult, optionalUser);

        if (bindingResult.hasErrors()) {
            logger.error("errors={}", bindingResult);
            return "user/login";
        }
        User loginUser = optionalUser.get();
        session.setAttribute(LOGIN_SESSION_NAME, loginUser);
        logger.info("{} 님이 로그인하셨습니다", loginUser.getNickname());
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
