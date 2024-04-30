package codesquad.springcafe.utils;

import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.replies.model.Reply;
import codesquad.springcafe.exception.ArticleAccessException;
import codesquad.springcafe.exception.ArticleCreationException;
import codesquad.springcafe.exception.ReplyAccessException;
import codesquad.springcafe.exception.UnauthorizedException;
import codesquad.springcafe.users.model.dto.UserPreviewDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthValidateService {
    private static final String SESSION_USER = "sessionedUser";

    public void validateSession(HttpSession session) {
        if (session == null || session.getAttribute(SESSION_USER) == null) {
            throw new UnauthorizedException("로그인 되지 않은 사용자 입니다.");
        }
    }

    public void validateWriterMatch(HttpSession session, String writer) {   // 게시글 작성자와 creationRequest의 writer가 같은 지 검증
        String sessionedUser = getSessionUser(session);
        if (!writer.equals(sessionedUser)) {
            throw new ArticleCreationException("게시글의 작성자와 로그인 한 사용자 정보가 일치하지 않습니다.");
        }
    }

    public void validateArticleAuth(HttpSession session, Article article) { // 게시글 작성자와 세션 유저가 같은 지 검증
        String sessionedUser = getSessionUser(session);

        if (!article.getUserId().equals(sessionedUser)) {
            throw new ArticleAccessException("게시글에 접근할 수 있는 권한이 없습니다.");
        }
    }

    public void validateReplyAuth(HttpSession session, Reply reply) {
        String sessionedUser = getSessionUser(session);

        if (!reply.getUserId().equals(sessionedUser)) {
            throw new ReplyAccessException("댓글에 접근할 수 있는 권한이 없습니다.");
        }
    }

    public String getSessionUser(HttpSession session) {
        return ((UserPreviewDto) session.getAttribute(SESSION_USER)).getUserId();
    }

}
