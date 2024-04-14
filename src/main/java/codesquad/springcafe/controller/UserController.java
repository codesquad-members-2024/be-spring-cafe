package codesquad.springcafe.controller;

import codesquad.springcafe.dto.User;
import codesquad.springcafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users") // 공유매핑
@Component
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 목록 조회하기
    @GetMapping("")
    public String users(Model model){
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users); // users 를 "users" 라는 이름으로 등록 => DB 에서 가져온 데이터를 users 라는 이름으로 뷰 페이지에서 사용 가능
        return "users/list";
    }

    // 회원 가입 양식
    @GetMapping("/signup")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new User());
        return "users/form";
    }

    // 회원 가입 처리
    @PostMapping("/signup")
    public String createUser(@ModelAttribute  User user, RedirectAttributes redirectAttributes){
        User savedUser = userService.signup(user);
        redirectAttributes.addAttribute("id", savedUser.getId());
        return "redirect:/users/{id}";
    }

    // 회원 상세 조회
    @GetMapping("{id}")
    public String showUserDetail(@PathVariable("id") Long id, Model model){
        Optional<User> userOptional = userService.findUserById(id);

        if (!userOptional.isPresent()){
            return "redirect:/users/list";
        }

        User user = userOptional.get();
        model.addAttribute("user", user);
        return "users/profile";
    }
}
