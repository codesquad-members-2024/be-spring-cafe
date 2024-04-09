package codesquad.springcafe.Controller;

import codesquad.springcafe.Domain.User;
import codesquad.springcafe.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


//file : Controller
@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService; // 스프링 생성할때 생성 -> 스프링 컨테이너의 서비스와 연결
    }
    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("/users")
    public String users() {
        return "user/list";
    }

    @PostMapping("/users")
    public String users(User user) { //html의 name과 매칭
        logger.info("info log = {}", user.getUserId());
        logger.debug("debug log = {}", user.getEmail());

        userService.signUp(user);
        return "redirect:/users";
    }

    @GetMapping("/users/list")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAllUser());
        return "redirect:/users";
    }
}