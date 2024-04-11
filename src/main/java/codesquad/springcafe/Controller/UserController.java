package codesquad.springcafe.Controller;

import codesquad.springcafe.Domain.User;
import codesquad.springcafe.Service.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService; // 스프링 생성할때 생성 -> 스프링 컨테이너의 서비스와 연결
    }

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping
    public String users(Model model) {
        List<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        model.addAttribute("usersSize", users.size());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable("userId") String userId,Model model) {
        //FIXME
        // - 유저 패스워드 같은 정보를 가져오는 경우 발생
        User user = userService.findUserById(userId);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("email", user.getEmail());
        return "user/profile";
    }

    @PostMapping
    public String users(User user, Model model) { //html의 name과 매칭
        try {
            logger.debug("debug log = {}", user.getEmail());
            logger.debug("debug log = {}", user.getUserId());
            logger.debug("debug log = {}", user.getPassword());
            userService.signUp(user);
        } catch (IllegalStateException e) {
            // 예외 처리를 진행합니다.
            logger.error("User registration failed: {}", e.getMessage());
            // todo:alert 처리
            return "redirect:users/form";
        }
        model.addAttribute(user);
        return "user/login_success";
    }
}