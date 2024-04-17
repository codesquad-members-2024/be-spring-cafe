package codesquad.springcafe.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") == null) {
            log.info("로그인 필요 - 요청 경로: {}", request.getRequestURI());
            response.sendRedirect("/user/login?return_to=" + request.getRequestURI());
            return false;
        }
        return true;
    }

}
