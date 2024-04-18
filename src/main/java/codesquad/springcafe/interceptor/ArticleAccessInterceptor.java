package codesquad.springcafe.interceptor;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.User;
import codesquad.springcafe.util.LoginUserProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

public class ArticleAccessInterceptor implements HandlerInterceptor {
    private final ArticleDatabase articleDatabase;

    public ArticleAccessInterceptor(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    /**
     * 다른 사용자 게시물 정보를 수정하거나 삭제하려고 한다면 403에러를 보내고 false를 리턴합니다.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        User loginUser = LoginUserProvider.provide(session);
        Long id = getPathVariable(request);

        Optional<Article> optionalArticle = articleDatabase.findBy(id);
        if (optionalArticle.isEmpty()) {
            response.sendRedirect("/");
            return false;
        }
        Article article = optionalArticle.get();

        if (loginUser == null || !loginUser.hasSameNickname(article.getWriter())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;

    }

    private Long getPathVariable(HttpServletRequest request) {
        Map<String, String> uriVariable = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return Long.parseLong(uriVariable.get("id"));
    }
}
