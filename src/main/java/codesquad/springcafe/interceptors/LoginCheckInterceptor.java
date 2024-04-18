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

        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute(SESSION_LOGIN);

        if (loginUser != null) return true;

        response.sendRedirect("/user/login.html");
        return false;
    }
}
