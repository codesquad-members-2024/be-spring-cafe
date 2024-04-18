package codesquad.springcafe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    UserDatabase userDatabase;

    @Autowired
    public LoginController(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserLoginDTO userLoginDTO, Model model) {
        if (!userDatabase.isExistUser(userLoginDTO.getUserid()) ||
            !userLoginDTO.isMatchedPwd(userDatabase.getUser(userLoginDTO.getUserid())
                                                    .getPassword())) {
            model.addAttribute("outputMessage", "아이디나 비밀번호가 맞지 않습니다.");
            return "/user/login_failed";
        }
        return "redirect:/";
    }
}
