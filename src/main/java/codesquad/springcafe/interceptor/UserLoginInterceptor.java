package codesquad.springcafe.interceptor;

import codesquad.springcafe.model.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession httpSession = request.getSession();
        Object value = httpSession.getAttribute("sessionUser");
        boolean isLoggedIn = (value != null); // userId가 null이 아니면 로그인 상태로 간주
        request.setAttribute("isLoggedIn", isLoggedIn);
        if (value != null & isLoggedIn) {
            SessionUser sessionUser = (SessionUser) value;
            request.setAttribute("navUserId", sessionUser.getUserId());
        }
        return true;
    }
}
