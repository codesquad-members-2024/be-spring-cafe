package codesquad.springcafe.controller;

import codesquad.springcafe.dto.user.UserLoginDTO;
import codesquad.springcafe.dto.user.UserSignupDTO;
import codesquad.springcafe.dto.user.UserInfoDTO;
import codesquad.springcafe.dto.user.UserUpdateDTO;
import codesquad.springcafe.model.User;
import codesquad.springcafe.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
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
    public String signUp(@ModelAttribute("user") UserSignupDTO userSignupDTO) {
        User newUser = userSignupDTO.toUser();
        userService.signUp(newUser);
        return "redirect:/users";
    }

    @GetMapping
    public String showList(Model model) {
        List<User> users = userService.findAll();
        List<UserInfoDTO> orderedUsers = LongStream.rangeClosed(1, users.size())
            .mapToObj(order -> users.get((int)order - 1).toDTO(order)).toList();
        model.addAttribute("users", orderedUsers);
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable("userId") String userId, Model model) {
        UserInfoDTO targetUser = userService.findById(userId).toDTO();
        model.addAttribute("user", targetUser);
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateForm(@PathVariable("userId") String userId, Model model) {
        UserInfoDTO targetUser = userService.findById(userId).toDTO();
        model.addAttribute("user", targetUser);
        return "/user/updateForm";
    }

    @GetMapping("/{userId}/update")
    public String tryUpdate(@PathVariable("userId") String userId, HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "/user/login_needed";
        }
        if (!loggedInUser.equals(userId)) {
            return "/user/update_failed";
        }
        return "/user/authenticate";
    }

    @PostMapping("/{userId}/authenticate")
    public String authenticate(@ModelAttribute("user") UserLoginDTO userLoginDTO, @PathVariable("userId") String userId) {
        Optional<User> loggedInUser = userService.authenticate(userLoginDTO.getUserId(), userLoginDTO.getPassword());
        if (loggedInUser.isEmpty()) {
            return "/user/authenticate_failed";
        }
        return "redirect:/users/" + userId + "/form";
    }

    @PutMapping("/{userId}/update")
    public String update(@ModelAttribute("user") UserUpdateDTO updateInfo, @PathVariable("userId") String userId, HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "/user/login_needed";
        }
        if (!loggedInUser.equals(userId)) {
            return "/user/update_failed";
        }

        userService.updateInfo(updateInfo.toUser(userId));
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserLoginDTO userLoginDTO, HttpSession session) {
        Optional<User> loggedInUser = userService.authenticate(userLoginDTO.getUserId(), userLoginDTO.getPassword());
        if (loggedInUser.isEmpty()) {
            return "/user/login_failed";
        }
        session.setAttribute("loggedInUser", loggedInUser.get().getUserId());
        String requestedUrl = (String) session.getAttribute("requestedUrl");
        if (requestedUrl == null || requestedUrl.isEmpty()) {
            return "redirect:/";
        }
        return "redirect:" + requestedUrl;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}