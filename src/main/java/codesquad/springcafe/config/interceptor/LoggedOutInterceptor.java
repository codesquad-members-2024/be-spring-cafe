package codesquad.springcafe.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggedOutInterceptor implements HandlerInterceptor {
    private static final String GET = "GET";
    private static final String POST = "POST";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String method = request.getMethod();

        if (method.equals(GET) && (session == null || session.getAttribute("sessionedUser") == null )) {
            response.sendRedirect("/users/login");
            return false; // Controller로 요청을 보내지 않는다.
        }

        return true;        // controller로 요청을 보낸다. [POST 처리]
    }
}
