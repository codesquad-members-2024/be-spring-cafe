package codesquad.springcafe.interceptor;

import codesquad.springcafe.controller.LoginController;
import codesquad.springcafe.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

public class UserProfileInterceptor implements HandlerInterceptor {
    /**
     * 다른 사용자의 프로필을 조회하거나 정보를 수정하려고 한다면 403에러를 보내고 false를 리턴합니다.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(LoginController.LOGIN_SESSION_NAME);

        String nickname = getPathVariable(request);
        if (loginUser == null || !loginUser.hasSameNickname(nickname)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;

    }

    private String getPathVariable(HttpServletRequest request) {
        Map<String, String> uriVariable = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return uriVariable.get("nickname");
    }
}
