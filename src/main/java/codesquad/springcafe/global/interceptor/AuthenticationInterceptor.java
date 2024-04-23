package codesquad.springcafe.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("userCredentials") == null) {
            // TODO: 원래 목적지를 redirect 파라미터로 추가
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
    }
}
