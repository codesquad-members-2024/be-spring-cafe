package codesquad.springcafe;

import codesquad.springcafe.db.UserDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
        UserDatabase.addUser(user);
        return "redirect:/users";
    }
}
