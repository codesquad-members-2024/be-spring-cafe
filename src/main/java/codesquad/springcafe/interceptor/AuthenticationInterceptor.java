package codesquad.springcafe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    public static final String LOGIN_USER_ID = "loginUserId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(LOGIN_USER_ID) == null) {
            response.sendRedirect("/users/login?redirectUri=" + requestURI);
            return false;
        }
        return true;
    }

}
