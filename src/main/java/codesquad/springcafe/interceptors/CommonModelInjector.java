package codesquad.springcafe.interceptors;

import codesquad.springcafe.domain.user.dto.UserIdentity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import static codesquad.springcafe.constants.Constant.*;

public class CommonModelInjector implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) return;

        HttpSession session = request.getSession(false);

        UserIdentity userIdentity = new UserIdentity("", "");
        boolean isLogin = false;

        if (session != null && session.getAttribute(SESSION_LOGIN) != null) {
            isLogin = true;
            userIdentity = (UserIdentity) session.getAttribute(SESSION_LOGIN);
        }

        modelAndView.addObject("isLogin", isLogin);
        modelAndView.addObject("loginUser", userIdentity);
    }
}
