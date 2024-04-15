package codesquad.springcafe;

import codesquad.springcafe.db.UserDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
        UserDatabase.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model){
        List<User> users = UserDatabase.findAllUsers();
        model.addAttribute("users", users);
        return "users/list";
    }

}
