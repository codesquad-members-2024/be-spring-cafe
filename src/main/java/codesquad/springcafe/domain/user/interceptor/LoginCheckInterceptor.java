package codesquad.springcafe.domain.user.interceptor;

import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        SimpleUserInfo loginUser = (SimpleUserInfo) session.getAttribute("loginUser");
        if (loginUser == null) {
            session.setAttribute("toGo", request.getRequestURI());

            response.sendRedirect("/user/login");
            return false;
        }
        return true;
    }
}
