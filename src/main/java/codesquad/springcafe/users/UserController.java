package codesquad.springcafe.users;

import db.UserDatabase;
import jakarta.servlet.http.HttpServletRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public String showUserForm() {
        return "/user/form";
    }

    @PostMapping("/users")
    public String registerUser(HttpServletRequest request) {
        // 폼 데이터를 처리하는 로직을 추가하고, 필요한 작업을 수행합니다.
        String email = request.getParameter("email");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User user = new User(email, userId, password);

        logger.debug("User Created : {}", user);

        UserDatabase.addUser(user);

        return "redirect:/users/list"; // 처리가 성공적으로 완료되면 리다이렉트할 경로를 반환합니다.
    }

    @GetMapping("/users/list")
    public String showUsers(Model model) {
        ArrayList<User> users = UserDatabase.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("totalUsers", users.size());

        return "/user/list";
    }

}
