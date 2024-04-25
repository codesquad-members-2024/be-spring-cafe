package codesquad.springcafe.util;

import codesquad.springcafe.controller.LoginController;
import codesquad.springcafe.form.user.LoginUser;
import jakarta.servlet.http.HttpSession;

/**
 * 세션 정보에 저장된 유저를 찾아서 반환합니다.
 */
public class LoginUserProvider {


    public static LoginUser provide(HttpSession session) {
        return (LoginUser) session.getAttribute(LoginController.LOGIN_SESSION_NAME);
    }
}
