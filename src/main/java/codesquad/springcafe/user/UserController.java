package codesquad.springcafe.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    UserDatabase userDatabase;

    public UserController(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    // 유저 등록 페이지
    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    // 유저 등록 메서드
    @PostMapping("/user/create")
    public String register(UserSignupDto userSignupDto) {
        userDatabase.save(userSignupDto.toEntity());
        return "redirect:/";
    }

    // 유저 목록 페이지
    @GetMapping("/user")
    public String list(Model model) {
        model.addAttribute("users", userDatabase.findAll());
        return "user/list";
    }

    // 유저 프로필 페이지
    @GetMapping("/user/profile")
    public String profile() {
        return "user/profile";
    }


}
