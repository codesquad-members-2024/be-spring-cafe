package codesquad.springcafe.interceptor;

import codesquad.springcafe.dto.user.UserInfoDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        Object loggedInUser = request.getSession().getAttribute("loggedInUser");
//        if (loggedInUser != null) {
//            // 세션에서 사용자 정보를 가져와서 모델에 추가
//            request.setAttribute("loggedInUser", loggedInUser);
//
//        }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception{
//        HttpSession session = request.getSession();
//    }
}
