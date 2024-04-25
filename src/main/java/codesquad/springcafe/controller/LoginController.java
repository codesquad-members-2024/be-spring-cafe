package codesquad.springcafe.controller;

import codesquad.springcafe.form.user.LoginForm;
import codesquad.springcafe.form.user.LoginUser;
import codesquad.springcafe.model.User;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.LoginService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
    private final LoginService loginService;
    private final ArticleService articleService;

    public LoginController(LoginService loginService, ArticleService articleService) {
        this.loginService = loginService;
        this.articleService = articleService;
    }

    /**
     * 사용자에게 로그인 폼을 보여줍니다.
     */
    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "user/login";
    }

    /**
     * 사용자가 작성한 로그인폼을 받아 이메일과 비밀번호 모두 일치하는 User를 찾으면 세션에 입력하고 사용자가 원하는 경로로 리다이렉트합니다.
     */
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        HttpSession session, @RequestParam(defaultValue = "/") String redirectUrl) {
        Optional<User> optionalUser = loginService.getMatchedUser(loginForm.getEmail(), loginForm.getPassword());
        if (optionalUser.isEmpty()) {
            bindingResult.reject("Wrong");
        }
        if (bindingResult.hasErrors()) {
            logger.error("errors={}", bindingResult);
            return "user/login";
        }
        User user = optionalUser.get();
        String nickname = user.getNickname();
        Set<Long> ownArticleIds = articleService.getArticleIds(nickname);

        LoginUser loginUser = new LoginUser(nickname, ownArticleIds);
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
}
