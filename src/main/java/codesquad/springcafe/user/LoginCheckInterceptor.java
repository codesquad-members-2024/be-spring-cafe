package codesquad.springcafe.user;

import codesquad.springcafe.user.UserDTO;
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

        UserDTO userDTO = (UserDTO) session.getAttribute("loginUser");
        if (userDTO == null) {
            response.sendRedirect("/user/login");
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
