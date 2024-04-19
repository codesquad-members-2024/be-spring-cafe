package codesquad.springcafe.controller;

import codesquad.springcafe.dto.LoginInformation;
import codesquad.springcafe.exception.LoginFailException;
import codesquad.springcafe.service.AccessService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccessController {
    private AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginInformation loginInformation, HttpSession session, Model model) {
        try {
            session.setAttribute("sessionUser", accessService.validateUser(loginInformation));
        } catch (LoginFailException e) {
           model.addAttribute("error", true);
           model.addAttribute("loginInformation", loginInformation);
           return "user/login";
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}