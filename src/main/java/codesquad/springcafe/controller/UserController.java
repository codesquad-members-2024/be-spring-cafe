package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.DB.UserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/main")
    public String showMain() {
        return "main";
    }

    @GetMapping("/users/form")
    public String showForm() {
        return "user/form";
    }

    @GetMapping("/users/success")
    // @RequestParam을 통해 쿼리문의 userId를 받아온다.
    // userId를 통해 DB에서 해당 사용자의 email, nickname을 받아온다.
    public String showLoginSuccess(@RequestParam String userId, Model model) {
        Optional<User> userOptional = UserDatabase.getUser(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("email", user.getEmail());
            model.addAttribute("nickname", user.getNickname());
        }

        return "/user/login_success";
    }

    @PostMapping("/user/create")
    // @ModelAttribute 어노테이션 을 통해 Post body를 파싱해 user객체로 반환한다.
    public String saveUser(@ModelAttribute User user) {
        UserDatabase.saveUser(user);
        logger.debug("new user: " + user.toString());

        // login success 페이지를 위해 쿼리로 userId 전달
        return "redirect:/users/success"+"?userId="+user.getUserId();
    }

    @GetMapping("/users/list")
    public String showUsers(Model model) {
        model.addAttribute("users", UserDatabase.getAllUsers()); // 전체 user 반환
        model.addAttribute("totalNumber", Integer.toString(UserDatabase.getTotalUserNumber())); //user 개수 반환

        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        Optional<User> userOptional = UserDatabase.getUser(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user); // profile.html에 user 객체 넘겨주기
        }

        return "/user/profile";
    }

}
