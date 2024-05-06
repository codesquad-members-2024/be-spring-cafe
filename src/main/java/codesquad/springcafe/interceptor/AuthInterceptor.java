package codesquad.springcafe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.NonNullApi;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        boolean isLoggedIn = session.getAttribute("loggedInUser") != null;

        if (!isLoggedIn) {
            String requestedUrl = request.getRequestURI();
            session.setAttribute("requestedUrl", requestedUrl);
            response.sendRedirect("/login");
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
