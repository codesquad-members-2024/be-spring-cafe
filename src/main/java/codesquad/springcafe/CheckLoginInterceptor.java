package codesquad.springcafe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CheckLoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CheckLoginInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        String viewName = null;
        if(modelAndView != null) {
            viewName = modelAndView.getViewName();
        }
        // 리다이렉트의 경우 ModelAndView가 AddObject된 요소들을 쿼리 파라미터에 담아 전달하기 때문에 조건문을 달았습니다.
        // Location : localhost:8080/;?loginUserId=dao
        if (viewName != null && !viewName.startsWith("redirect:")) {
            if (session != null) {
                String loginUserId = (String) session.getAttribute("loginUserId");
                if (loginUserId != null) {
                    modelAndView.addObject("loginUserId", loginUserId);
                }
            }
        }
    }
}
