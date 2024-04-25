package codesquad.springcafe.interceptor;

import codesquad.springcafe.util.LoginUserProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriUtils;

public class LoginCheckInterceptor implements HandlerInterceptor {
    /**
     * 인증되지 않은 사용자가 보안페이지에 접근시 로그인 페이지로 리다이렉트시키고 redirectUrl을 파라미터로 넘깁니다.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || LoginUserProvider.provide(session) == null) {
            String redirectUrl = UriUtils.encode(request.getRequestURI(), StandardCharsets.UTF_8);
            response.sendRedirect("/login?redirectUrl=" + redirectUrl);
            return false;
        }
        return true;
    }
}
