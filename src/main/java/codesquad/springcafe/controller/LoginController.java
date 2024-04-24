package codesquad.springcafe.controller;

import codesquad.springcafe.db.user.UserDatabase;
import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserCredentialDto;
import codesquad.springcafe.model.user.dto.UserProfileDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    public LoginController(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
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
            HttpServletResponse response,
            Model model
    ) throws IOException
    {
        Optional<UserCredentialDto> userCredentialsOpt = userDatabase.getUserCredential(userInput.getUserId());
        if(userCredentialsOpt.isEmpty() || !userInput.equals(userCredentialsOpt.get())){
            model.addAttribute("validationError", true);
            return "users/login";
        }

        Optional<UserProfileDto> userProfileOpt = userDatabase.findUserByUserId(userInput.getUserId());
        if(userProfileOpt.isEmpty()){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        UserProfileDto userProfile = userProfileOpt.get();
        session.setAttribute(LOGIN_SESSION_NAME, userProfile);
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
