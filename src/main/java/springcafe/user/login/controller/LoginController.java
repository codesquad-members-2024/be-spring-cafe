package springcafe.user.login.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springcafe.user.dto.AuthInfo;
import springcafe.user.login.dto.LoginCommand;
import springcafe.user.login.service.LoginService;
import springcafe.user.model.User;

@Controller
@RequestMapping("/user")
public class LoginController {

    Logger logger = LoggerFactory.getLogger("LoginController");

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String login(LoginCommand loginCommand, @CookieValue(value = "REMEMBER", required = false)Cookie rCookie) {

        if(rCookie!=null){
            loginCommand.setUserId(rCookie.getValue());
            loginCommand.setRememberUserId(true);
        }

        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginCommand loginCommand, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }

        String userId = loginCommand.getUserId();
        String password = loginCommand.getPassword();


        User user = loginService.loginCheck(userId, password);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        //유저 아이디도 암호화해서 쿠키에 저장

        Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getUserId());
        rememberCookie.setPath("/");
        if(loginCommand.isRememberUserId()){
            rememberCookie.setMaxAge(60*60*24*30);
        } else {
            rememberCookie.setMaxAge(0);
        }

        response.addCookie(rememberCookie);

        return "index";
    }

    @GetMapping("/login_failed")
    public String loginFail(){
        return "user/login_failed";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();

        return "index";
    }
}
