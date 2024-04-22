package codesquad.springcafe.Controller;

import codesquad.springcafe.Domain.User;
import codesquad.springcafe.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService; // 스프링 생성할때 생성 -> 스프링 컨테이너의 서비스와 연결
    }

    @GetMapping
    public String users(Model model) {
        List<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        model.addAttribute("usersSize", users.size());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        //todo - 유저 패스워드 같은 정보를 가져오지 않도록 변경
        try {
            User user = userService.findUserById(userId);
            model.addAttribute("userId", user.getUserId());
            model.addAttribute("email", user.getEmail());
        } catch (NoSuchElementException e) {
            //존재하지 않는 유저 가져오면 리스트로 리다이렉트
            logger.error(e.getMessage());
            return "redirect:/users";
        }
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String update(@PathVariable("userId") String userId, Model model) {
        try {
            User user = userService.findUserById(userId);
            model.addAttribute("userId", user.getUserId());
            model.addAttribute("email", user.getEmail());
        } catch (NoSuchElementException e) {
            //존재하지 않는 유저 가져오면 리스트로 리다이렉트
            logger.error(e.getMessage());
            return "redirect:/users";
        }
        return "user/update";
    }

    //   todo : 유저 업데이트
    //    로그인한 사용자는 자신의 정보를 수정할 수 있어야 한다.
    //    이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
    //    비밀번호가 일치하는 경우에만 수정 가능하다.
    @PutMapping("/update")
    public String update(@ModelAttribute User updateUser, HttpSession httpSession) {
        String userId = (String)httpSession.getAttribute("loginUserId");

        //유저 정보가 없거나, 유저아이디와 변경할 아이디가 같지 않은경우 에러페이지 출력
        if (userId.isEmpty() || !updateUser.getUserId().equals(userId)) {
            return "redirect:/index";
        }

        try{
            userService.update(updateUser);
        }catch (Exception e){
            logger.error("User update failed: {}", e.getMessage());
            return "redirect:/index";
        }

        return "redirect:/users";
    }

    @PostMapping
    public String signUp(User user, Model model) { //html의 name과 매칭
        try {
            userService.signUp(user);
        } catch (IllegalStateException e) {
            return "redirect:users/form";
        }
        model.addAttribute(user);
        return "user/login_success";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
        @RequestParam("password") String password,
        HttpSession httpSession) {

        try {
            String loginUserId = userService.login(email, password);

            if (loginUserId.isEmpty()) {
                return "redirect:/users/loginForm";
            }
            //로그인 성공시 httpSession에 정보를 넣는다
            httpSession.setAttribute("loginUserId", loginUserId);

            return "redirect:/";
        } catch (NoSuchElementException e) { //에러 발생하면
            logger.error("User login failed: {}", e.getMessage());
            return "redirect:/users/loginForm";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

}