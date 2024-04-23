package codesquad.springcafe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class CheckAuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CheckAuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("loginUserId") == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
