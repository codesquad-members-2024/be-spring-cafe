package codesquad.springcafe.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public class CsrfTokenIntercetor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CsrfTokenIntercetor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // POST, PUT, DELETE일 때만 csrf token 체크
        if (!request.getMethod().equals("POST") || !request.getMethod().equals("PUT") || !request.getMethod().equals("DELETE")) {
            return true;
        }

        HttpSession httpSession = request.getSession();
        String csrfToken = request.getParameter("CSRF_TOKEN");
        Object sessionCsrfToken = httpSession.getAttribute("CSRF_TOKEN");
        httpSession.removeAttribute("CSRF_TOKEN");

        if (csrfToken != null && sessionCsrfToken != null && csrfToken.equals(sessionCsrfToken.toString())) {
            return true;
        }

        response.sendRedirect("/");
        logger.error("CSRF(Cross-Site Request Forgery) 보안 검사에서 문제가 발생했습니다. | original: {} | sent : {}", csrfToken,
                sessionCsrfToken == null ? "null" : sessionCsrfToken.toString());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("CSRF_TOKEN") == null) {
            String csrfToken = UUID.randomUUID().toString();
            session.setAttribute("CSRF_TOKEN", csrfToken);
        }
    }
}
