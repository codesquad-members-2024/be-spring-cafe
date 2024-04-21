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
            String redirectUrl = request.getRequestURI();
            response.sendRedirect("/users/login?redirectUrl=" + redirectUrl);
            return false;
        }
        return true;
    }

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestURI = request.getRequestURI();
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute(LOGIN_USER_ID) == null) {
//            response.sendRedirect("/users/login?redirectURL=" + requestURI);
//            return false;
//        }
//        return true;
//    }
}
