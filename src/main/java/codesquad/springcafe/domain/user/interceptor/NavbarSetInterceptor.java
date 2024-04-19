package codesquad.springcafe.domain.user.interceptor;

import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class NavbarSetInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        SimpleUserInfo loginUser = (SimpleUserInfo) request.getSession().getAttribute("loginUser");
        if (loginUser != null) {
            request.setAttribute("user_section", true);
        }
    }
}
