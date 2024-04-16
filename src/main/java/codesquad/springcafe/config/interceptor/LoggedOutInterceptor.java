package codesquad.springcafe.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggedOutInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggedOutInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("sessionedUser") == null) {
            response.sendRedirect("/users/login");
            return false; // Controller로 요청을 보내지 않는다.
        }
        return true;        // controller로 요청을 보낸다.
    }
}
