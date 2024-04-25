package codesquad.springcafe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //controller 전에 로그인 여부 확인
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        logger.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession httpSession = request.getSession(false);

        if (httpSession == null || httpSession.getAttribute("loginUserId") == null) {
            logger.info("미인증 사용자 요청");
            //로그인으로 리다이렉트
            response.sendRedirect("/users/loginForm?redirectURL=" + requestURI);
            return false;
        }
        //true
        return true;
    }

}
