package codesquad.springcafe.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public String createUser(@ModelAttribute User user) {
        try {
            userService.create(user);
        } catch (IllegalArgumentException alreadyExistsId) {
            return "redirect:/user/form/fail";
        }

        log.debug(user.toString());
        return "redirect:/user/users";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userId") String id,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {

        UserDTO userDTO;
        if ((userDTO = userService.login(id, password)) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", userDTO);
            session.setMaxInactiveInterval(60 * 30);

            log.info("로그인됨 : " + id);
        }

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") String id,
                         @RequestParam("password") String password,
                         @RequestParam("name") String name,
                         @RequestParam("email") String email,
                         Model model) {

        if (userService.update(new User(id, password, name, email))) {
            model.addAttribute("alert", "회원 정보 변경 성공!");
        } else {
            model.addAttribute("alert", "비밀번호가 일치하지 않습니다!");
        }

        model.addAttribute("alert_section", true);
        model.addAttribute("userId", id);
        return "user/update_form";
    }

    @GetMapping("/form")
    public String userForm(Model model) {
        model.addAttribute("alert", "");
        model.addAttribute("alert_section", false);
        return "user/form";
    }

    @GetMapping("/form/fail")
    public String userFormWithAlert(Model model) {

        // 사용자에게 알림
        model.addAttribute("alert", "이미 존재하는 ID 입니다");
        model.addAttribute("alert_section", true);
        return "user/form";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        List<UserDTO> users = userService.userList();

        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") String id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user/profile";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("alert", "");
        model.addAttribute("alert_section", false);
        model.addAttribute("userId", id);

        return "user/update_form";
    }

    @GetMapping("/update")
    public String myUpdateForm(HttpServletRequest request, Model model) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

        model.addAttribute("alert", "");
        model.addAttribute("alert_section", false);
        model.addAttribute("userId", loginUser.id());

        return "user/update_form";
    }
}
