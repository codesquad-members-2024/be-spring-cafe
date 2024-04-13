package codesquad.springcafe.user.interceptor;

import codesquad.springcafe.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        session.getAttribute("userId");
        session.getAttribute("password");

        SimpleUserInfo loginUser = (SimpleUserInfo) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("/user/login");
            return false;
        }
        return true;
    }
}
