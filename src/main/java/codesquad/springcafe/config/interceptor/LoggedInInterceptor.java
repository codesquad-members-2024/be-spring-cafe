package codesquad.springcafe.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggedInInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("sessionedUser") == null) {
            return true;   // controller로 요청을 보낸다. -> 로그인 페이지
        }
        response.sendRedirect("/");
        return false;        // controller로 요청을 보내지 않는다.
    }
}