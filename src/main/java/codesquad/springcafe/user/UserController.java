package codesquad.springcafe.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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
    @GetMapping("/user/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userDatabase.findByUserId(userId));
        return "user/profile";
    }

    // 유저 정보 수정 페이지
    @GetMapping("/user/{userId}/form")
    public String updateProfilePage(@PathVariable String userId, Model model) {
        model.addAttribute("user", userDatabase.findByUserId(userId));
        return "user/update_form";
    }


    //수정된 회원 정보 post
    @PutMapping("/user/{userId}/form")
    public String putUpdateProfile(@PathVariable String userId, UserUpdateDto userUpdateDto) {
        User foundUser = userDatabase.findByUserId(userId);
        if (foundUser.checkPassword(userUpdateDto.getPassword())) {
            User updatedUser = new User(userId, userUpdateDto.getEmail(),
                userUpdateDto.getNickname(), userUpdateDto.getPassword());
            userDatabase.updateUser(updatedUser, userId);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return "redirect:/user";
    }

}
