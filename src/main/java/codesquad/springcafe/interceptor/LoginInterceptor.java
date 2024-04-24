package codesquad.springcafe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import static codesquad.springcafe.controller.UserController.LOGIN_USER_ID;

public class LoginInterceptor implements HandlerInterceptor {

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
