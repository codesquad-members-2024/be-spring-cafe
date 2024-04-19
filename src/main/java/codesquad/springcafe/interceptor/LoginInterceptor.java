package codesquad.springcafe.interceptor;

import codesquad.springcafe.controller.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(SessionConst.SESSION_ID) == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
