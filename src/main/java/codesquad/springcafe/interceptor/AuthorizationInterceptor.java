package codesquad.springcafe.interceptor;

import codesquad.springcafe.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            // 현재 URL을 redirectUrl로 세션에 저장
            String redirectUrl = request.getRequestURI();
            request.getSession().setAttribute("redirectUrl", redirectUrl);
            // 로그인 페이지로 redirect
            response.sendRedirect("/users/login");
        }
        return true;
    }
}
