package codesquad.springcafe.Controller;

import codesquad.springcafe.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


//file : Controller
@Controller
public class UserController {

    //    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @GetMapping("/user/form.html")
    public String form() {
        return "user/form";
    }
    @PostMapping("/users")
    public String users(@ModelAttribute User user, Model model) {

        return "user/form";
    }
}