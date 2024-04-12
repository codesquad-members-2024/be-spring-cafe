package codesquad.springcafe;

import codesquad.springcafe.database.UserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MyController {

    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
        UserDatabase.addUser(user);
        logger.debug("add user : {}", user);
        return "redirect:/users";
    }

}
