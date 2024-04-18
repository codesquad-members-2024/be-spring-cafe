package codesquad.springcafe.interceptors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import static codesquad.springcafe.constants.Constant.*;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().contentEquals("POST")) return true;

        HttpSession session = request.getSession(false);
        if (session != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (validateCookie(cookie, session)) {
                    return true;
                }
            }
        }

        response.sendRedirect("/user/login.html");
        return false;
    }

    private boolean validateCookie(Cookie cookie, HttpSession session) {
        if (cookie.getName().contentEquals(SESSION_LOGIN)) {
            return session.getAttribute(cookie.getValue()) != null;
        }
        return false;
    }
}
