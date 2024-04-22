package codesquad.springcafe.controller.login;

import codesquad.springcafe.controller.SessionConst;
import codesquad.springcafe.controller.argumentresolver.LoginId;
import codesquad.springcafe.domain.member.Member;
import codesquad.springcafe.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final MemberService memberService;

    @Autowired
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("form") loginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("form") loginForm form, BindingResult bindingResult,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        /* 로그인 시도 */
        Optional<Member> loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember.isEmpty()) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        /* 로그인 성공: 세션에 멤버 id 저장 */
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.SESSION_ID, form.getLoginId());

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("logout");
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("/api/currentUser")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getLoginUserId(@LoginId String loginId) {
        Map<String, String> data = Collections.singletonMap("username", loginId);

        return ResponseEntity.ok(data);
    }
}
