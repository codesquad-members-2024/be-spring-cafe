package codesquad.springcafe.advice;

import codesquad.springcafe.model.User;
import codesquad.springcafe.util.LoginUserProvider;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserAdvice {
    /**
     * 로그인된 사용자를 모든 html의 navbar에서 보여주기 위해 모델로 설정합니다.
     */
    @ModelAttribute("loginUser")
    public User populateUser(HttpSession session) {
        return LoginUserProvider.provide(session);
    }
}
