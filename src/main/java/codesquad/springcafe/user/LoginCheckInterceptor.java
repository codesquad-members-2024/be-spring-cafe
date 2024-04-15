package codesquad.springcafe.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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

        request.setAttribute("user", userDTO);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            UserDTO userDTO = (UserDTO) request.getAttribute("user");
            modelAndView.addObject("userName", userDTO.name());
        }
    }
}
