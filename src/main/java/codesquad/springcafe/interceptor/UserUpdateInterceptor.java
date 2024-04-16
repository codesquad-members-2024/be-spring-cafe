package codesquad.springcafe.interceptor;

import codesquad.springcafe.model.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserUpdateInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        HttpSession httpSession = request.getSession();
        Object value = httpSession.getAttribute("sessionUser");
        String[] pathParts = request.getRequestURI().split("/");
        String pathUserId = pathParts[pathParts.length - 1]; // 마지막 부분을 가져옴
        if (value != null) {
            SessionUser sessionUser = (SessionUser) value;
            if (!sessionUser.getUserId().equals(pathUserId)) {
                response.sendRedirect("/users/invalid-modify");
                return false;
            }
        }
        return true;
    }
}
