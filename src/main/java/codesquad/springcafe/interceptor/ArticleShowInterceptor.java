package codesquad.springcafe.interceptor;

import codesquad.springcafe.model.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class ArticleShowInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String[] pathParts = request.getRequestURI().split("/");
        Long articleId = Long.parseLong(pathParts[pathParts.length - 1]); // 마지막 부분을 가져옴

        HttpSession httpSession = request.getSession();
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");

        boolean isWriter = sessionUser.getArticleIds().contains(articleId);
        request.setAttribute("isWriter", isWriter);
        return true;
    }
}
