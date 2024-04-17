package codesquad.springcafe.interceptor;

import codesquad.springcafe.controller.LoginController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
    /**
     * 인증되지 않은 사용자가 보안페이지에 접근시 로그인 페이지로 리다이렉트시키고 redirectUrl을 파라미터로 넘깁니다.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(LoginController.LOGIN_SESSION_NAME) == null) {
            response.sendRedirect("/login?redirectUrl=" + request.getRequestURI());
            return false;
        }
        return true;
    }
}
