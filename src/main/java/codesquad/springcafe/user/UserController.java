package codesquad.springcafe.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 등록 페이지
    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    // 유저 등록 메서드
    @PostMapping("/create")
    public String register(UserSignupDto userSignupDto) {
        userService.save(userSignupDto.toEntity());
        return "redirect:/";
    }

    // 유저 목록 페이지
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    // 유저 프로필 페이지
    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findByUserId(userId));
        return "user/profile";
    }

    // 유저 정보 수정 페이지
    @GetMapping("{userId}/form")
    public String updateProfilePage(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findByUserId(userId));
        return "user/update_form";
    }


    //수정된 회원 정보 post
    @PutMapping("{userId}/form")
    public String putUpdateProfile(@PathVariable String userId, UserUpdateDto userUpdateDto) {
        userService.updateUserProfile(userUpdateDto, userId);
        return "redirect:/user";
    }

}
