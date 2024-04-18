package codesquad.springcafe.user;

import codesquad.springcafe.exceptions.CanNotLoginException;
import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.LoginUser;
import codesquad.springcafe.user.domain.User;
import codesquad.springcafe.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import static codesquad.springcafe.constants.Constant.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getAllUsersAsList());
        return "/user/list";
    }

    @GetMapping("/profile/{name}")
    public String showUserProfile(Model model, @PathVariable String name) throws NoSuchUserException{
        User user = userService.findUserByName(name);

        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/profile")
    public String showLoginUserProfile(Model model, HttpSession session, @CookieValue(name = SESSION_LOGIN) String sessionId) throws NoSuchUserException{
        String userId = (String) session.getAttribute(sessionId);
        User user = userService.findUserById(userId);

        model.addAttribute("user", user);

        return "user/profile";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginUser loginUser, HttpSession session, HttpServletResponse response) throws CanNotLoginException {
        String sessionKey = userService.loginVerification(loginUser);

        session.setAttribute(sessionKey, loginUser.getUserId());

        Cookie cookie = new Cookie(SESSION_LOGIN, sessionKey);
        cookie.setPath("/");

        response.addCookie(cookie);
        return "redirect:/";
    }
}
