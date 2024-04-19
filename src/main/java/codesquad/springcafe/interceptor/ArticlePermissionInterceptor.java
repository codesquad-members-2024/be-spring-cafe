package codesquad.springcafe.interceptor;

import codesquad.springcafe.model.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.web.servlet.HandlerInterceptor;

public class ArticlePermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        String[] pathParts = request.getRequestURI().split("/");
        Long articleId = Long.parseLong(pathParts[pathParts.length - 1]); // 마지막 부분을 가져옴

        HttpSession httpSession = request.getSession();
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");

        boolean isWriter = sessionUser.getArticleIds().contains(articleId);

        if (isWriter) { // 세션 사용자가 생성한 게시글이라면
            return true;
        }
        response.sendRedirect("/article/invalid-modify");
        return false;
    }
}
