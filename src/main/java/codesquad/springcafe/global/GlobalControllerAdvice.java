package codesquad.springcafe.global;

import codesquad.springcafe.model.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request) {
        // navbarFlag의 기본값을 true로 설정하여 모델에 추가
        model.addAttribute("navbarFlag", true);

        // 사용자의 로그인 여부와 로그인 여부에 따른 사용자 이름값을 모델에 추가
        HttpSession httpSession = request.getSession();
        Object value = httpSession.getAttribute("sessionUser");
        boolean isLoggedIn = (value != null); // 세션 사용자가 null이 아니면 로그인 상태로 간주
        model.addAttribute("isLoggedIn", isLoggedIn);
        if (isLoggedIn) {
            SessionUser sessionUser = (SessionUser) value;
            model.addAttribute("navUserId", sessionUser.getUserId());
        }

        // 로그인 페이지로 리다이렉트된 이유에 따라 페이지의 타이틀을 설정하기 위해 모델에 추가
        String redirectUrl = request.getParameter("redirectUrl");
        String redirectMsg = getRedirectFlag(redirectUrl);
        model.addAttribute("redirectMsg", redirectMsg);
    }

    private String getRedirectFlag(String redirectUrl) {
        if (redirectUrl != null) { // 권한이 없어 로그인 페이지로 리다이렉트된 것이라면
            return "푸카페 회원만 접근 가능한 페이지입니다.";
        }
        return "로그인"; // 바로 로그인 페이지에 접근한 것이라면
    }
}
