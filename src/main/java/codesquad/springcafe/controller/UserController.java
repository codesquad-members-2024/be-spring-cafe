package codesquad.springcafe.controller;

import codesquad.springcafe.dto.user.LoginDTO;
import codesquad.springcafe.dto.user.SignUpDTO;
import codesquad.springcafe.dto.user.UserInfoDTO;
import codesquad.springcafe.dto.user.UserUpdateDTO;
import codesquad.springcafe.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String signUp(@ModelAttribute("user") SignUpDTO signUpDTO, Model model) {
        UserInfoDTO newUser = userService.signUp(signUpDTO);
        model.addAttribute("user", newUser);
        return "redirect:/users";
    }

    @GetMapping
    public String showList(Model model) {
        List<UserInfoDTO> users = userService.findAll();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable("userId") String userId, Model model) {
        UserInfoDTO targetUser = userService.findById(userId);
        model.addAttribute("user", targetUser);
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateInfoForm(@PathVariable("userId") String userId, Model model) {
        UserInfoDTO targetUser = userService.findById(userId);
        model.addAttribute("user", targetUser);
        return "/user/updateForm";
    }

    @GetMapping("/{userId}/update")
    public String tryUpdateInfo(@PathVariable String userId, HttpSession session, Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "/user/login_needed";
        }
        UserInfoDTO user = (UserInfoDTO) loggedInUser;
        if (!user.getUserId().equals(userId)) {
            return "/user/update_failed";
        }
        return "/user/authenticate";
    }

    @PostMapping("/{userId}/authenticate")
    public String authenticate(@ModelAttribute("user") LoginDTO loginDTO, @PathVariable String userId) {
        Optional<UserInfoDTO> loggedInUser = userService.authenticate(loginDTO);
        if (loggedInUser.isEmpty()) {
            return "/user/login_needed";
        }
        return "redirect:/users/" + userId + "/form";
    }

    @PutMapping("/{userId}/update")
    public String updateInfo(@ModelAttribute("user") UserUpdateDTO updateInfo, @PathVariable String userId, Model model) {
        UserInfoDTO updatedUser = userService.updateInfo(userId, updateInfo);
        model.addAttribute("user", updatedUser);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") LoginDTO loginDTO, HttpSession session) {
        Optional<UserInfoDTO> loggedInUser = userService.authenticate(loginDTO);
        if (loggedInUser.isEmpty()) {
            return "/user/login_failed";
        }
        session.setAttribute("loggedInUser", loggedInUser.get());
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/";
    }
}