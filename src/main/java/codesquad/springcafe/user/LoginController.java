package codesquad.springcafe.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    UserDatabase userDatabase;
    UserCrednetialService userCrednetialService;

    @Autowired
    public LoginController(UserDatabase userDatabase, UserCrednetialService userCrednetialService) {
        this.userDatabase = userDatabase;
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserLoginDTO userLoginDTO, HttpServletRequest request, Model model) {
        if (!userCrednetialService.checkValidCredential(userLoginDTO)) {
            model.addAttribute("outputMessage", "아이디나 비밀번호가 맞지 않습니다.");
            return "/user/login_failed";
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginUserId", userLoginDTO.getUserid());
        return "redirect:/";
    }

    @PostMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 세션 만료
            session.invalidate();
        }
        return "redirect:/";
    }
}
