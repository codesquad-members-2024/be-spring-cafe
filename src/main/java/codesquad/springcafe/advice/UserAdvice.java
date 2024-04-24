package codesquad.springcafe.advice;

import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserProfileDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserAdvice {

    @ModelAttribute
    public void registerLoginInfo(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("springCafeMember") != null;
        model.addAttribute("loginUser", isLoggedIn);
        if(isLoggedIn){
            UserProfileDto userProfile = (UserProfileDto) session.getAttribute("springCafeMember");
            model.addAttribute("userId", userProfile.getUserId());
        }
    }
}
