package codesquad.springcafe.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class CsrfTokenIntercetor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // POST일 때만 csrf token 체크
        if (!request.getMethod().equals("POST")) {
            return true;
        }

        HttpSession httpSession = request.getSession();
        String csrfToken = request.getParameter("CSRF_TOKEN");
        Object csrfTokenSession = httpSession.getAttribute("CSRF_TOKEN");

        if (csrfToken == null || !csrfToken.equals(csrfTokenSession.toString())) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
