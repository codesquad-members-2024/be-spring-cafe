package codesquad.springcafe.user.interceptor;

import codesquad.springcafe.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class NavbarSetInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        SimpleUserInfo loginUser = (SimpleUserInfo) request.getSession().getAttribute("loginUser");
        if (modelAndView != null && loginUser != null) {
            modelAndView.addObject("navbarUser", loginUser);
            modelAndView.addObject("user_section", true);
        }
    }
}
