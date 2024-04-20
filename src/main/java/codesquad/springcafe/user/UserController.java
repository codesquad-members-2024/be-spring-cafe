package codesquad.springcafe.user;

import codesquad.springcafe.exceptions.CanNotLoginException;
import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.LoginUser;
import codesquad.springcafe.user.domain.User;
import codesquad.springcafe.user.domain.UserIdentity;
import codesquad.springcafe.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.Map;

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
    public String showUserProfile(Model model, @PathVariable String name, HttpSession session) throws NoSuchUserException {
        UserIdentity loginUser = (UserIdentity) session.getAttribute(SESSION_LOGIN);
        User user = userService.findUserByName(name);

        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/profile")
    public String showLoginUserProfile(Model model, HttpSession session) throws NoSuchUserException {
        UserIdentity loginUser = (UserIdentity) session.getAttribute(SESSION_LOGIN);
        User user = userService.findUserById(loginUser.getUserId());

        model.addAttribute("user", user);

        return "user/profile";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginUser loginUser, HttpSession session, HttpServletResponse response) throws CanNotLoginException {
        UserIdentity userIdentity = userService.loginVerification(loginUser);

        session.setAttribute(SESSION_LOGIN, userIdentity);

        String go = (String) session.getAttribute(LOGIN_AFTER_REDIRECT);
        if (go == null) return "redirect:/";

        session.removeAttribute(LOGIN_AFTER_REDIRECT);

        return "redirect:" + go;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login.html";
    }

    @GetMapping("/update")
    public String showUserProfileUpdateForm(Model model, HttpSession session) throws NoSuchUserException {
        UserIdentity loginUser = (UserIdentity) session.getAttribute(SESSION_LOGIN);
        User user = userService.findUserById(loginUser.getUserId());

        model.addAttribute("user", user);

        return "user/update_form";
    }

    @PostMapping("/update")
    public String updateUserProfile(@ModelAttribute User after) throws NoSuchUserException {
        User before = userService.findUserById(after.getUserId());

        userService.updateUser(before, after);

        return "redirect:/user/profile";
    }

    @PostMapping("/duplicate_check")
    @ResponseBody
    public Map<String, Boolean> checkDuplicate(@RequestBody String requestJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(requestJson);
        String value = rootNode.path("value").asText();

        boolean result = userService.checkValueIsDuplicate(value);

        return Collections.singletonMap("check", result);
    }
}
