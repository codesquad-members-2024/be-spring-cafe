package codesquad.springcafe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserPermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("sessionUser") == null) {
            // 원래 요청한 URL을 저장
            String redirectUrl = request.getRequestURI();
            response.sendRedirect("/users/login?redirectUrl=" + redirectUrl);
            return false;
        }
        return true;
    }
}
