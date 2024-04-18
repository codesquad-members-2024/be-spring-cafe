package codesquad.springcafe.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    UserDatabase userDB;

    @Autowired
    UserController(UserDatabase userDatabaseH) {
        this.userDB = userDatabaseH;
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
        if (userDB.isExistUser(user.getUserId()) == true) {
            return "redirect:/users/form/" + user.getUserId();
        }
        userDB.addUser(user);
        logger.debug("add user : {}", user.getUserId());
        return "redirect:/users";
    }

    @GetMapping("/users/form/{userid}")
    public String failCreateUser(@PathVariable String userid, Model model) {
        model.addAttribute("outputMessage", "이미 존재하는 회원입니다." + userid);
        return "user/failed";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> userList = userDB.getUserList();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    @GetMapping("/users/{userid}")
    public String showUser(@PathVariable String userid, Model model) {
        User user = userDB.getUser(userid);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{userid}/form")
    public String showUserEditForm(@PathVariable String userid, Model model) {
        model.addAttribute("userid",  userid);
        return "user/updateForm";
    }

    @PutMapping("/users/{userid}")
    public String updateUser(@ModelAttribute User editedUser) {
        User oldUser = userDB.getUser(editedUser.getUserId());
        oldUser.updateUser(editedUser.getPassword(), editedUser.getName(), editedUser.getEmail());
        logger.info("update user : {}", oldUser.getUserId());
        return "redirect:/users";
    }

}
