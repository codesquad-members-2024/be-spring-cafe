package codesquad.springcafe.controller;

import codesquad.springcafe.db.user.UserDatabase;
import codesquad.springcafe.model.user.dto.UserCredentialDto;
import codesquad.springcafe.security.PasswordEncoder;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Optional;

@Controller
public class LoginController {

    public static final String LOGIN_SESSION_NAME = "springCafeMember";

    private final UserDatabase userDatabase;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserDatabase userDatabase, PasswordEncoder passwordEncoder){
        this.userDatabase = userDatabase;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "users/login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute UserCredentialDto userInput,
            @RequestParam(defaultValue = "/") String redirectURL,
            HttpSession session,
            Model model)
    {
        Optional<UserCredentialDto> userCredentialsOpt = userDatabase.getUserCredential(userInput.getUserId());

        if(userCredentialsOpt.isEmpty() ||
                !passwordEncoder.matches(userInput.getPassword(), userCredentialsOpt.get().getPassword())){
            model.addAttribute("validationError", true);
            return "users/login";
        }

        session.setAttribute(LOGIN_SESSION_NAME, userInput.getUserId());
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
