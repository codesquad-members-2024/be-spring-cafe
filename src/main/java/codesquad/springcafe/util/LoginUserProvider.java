package codesquad.springcafe.util;

import codesquad.springcafe.model.User;
import jakarta.servlet.http.HttpSession;

/**
 * 세션 정보에 저장된 유저를 찾아서 반환합니다.
 */
public class LoginUserProvider {
    public static final String LOGIN_SESSION_NAME = "loginUser";

    public static User provide(HttpSession session) {
        return (User) session.getAttribute(LOGIN_SESSION_NAME);
    }
}
