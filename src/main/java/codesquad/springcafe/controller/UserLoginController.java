package codesquad.springcafe.controller;

import codesquad.springcafe.dto.UserLoginDto;
import codesquad.springcafe.exception.service.UserNotJoinedException;
import codesquad.springcafe.model.LoginUser;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserLoginController {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    private final UserService userService;
    private final ArticleService articleService;

    @Autowired
    public UserLoginController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("/login")
    public String showLoginPage(@ModelAttribute("userLoginDto") UserLoginDto userLoginDto) {
        return "user/login";
    }

    @PostMapping("/login")
    public String processLoginForm(@Valid @ModelAttribute UserLoginDto userLoginDto, BindingResult bindingResult,
                                   HttpSession httpSession, @RequestParam(defaultValue = "/") String redirectUrl) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }

        try {
            LoginUser loginUser = userLoginDto.creatLoginUser();
            if (userService.isJoinedUser(loginUser)) {
                setSessionUser(loginUser.getUserId(), httpSession);
                return "redirect:" + redirectUrl;
            }
        } catch (UserNotJoinedException e) {
            logger.error(e.getMessage());
        }
        bindingResult.reject("isNotJoinedUser");
        return "user/login";
    }

    private void setSessionUser(String userId, HttpSession httpSession) {
        SessionUser sessionUser = userService.findSessionUserById(userId);
        List<Long> articleIds = articleService.findUserArticleIds(userId);
        sessionUser.setArticleIds(articleIds);

        httpSession.setAttribute("sessionUser", sessionUser);
        httpSession.setMaxInactiveInterval(3600);
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }
}
