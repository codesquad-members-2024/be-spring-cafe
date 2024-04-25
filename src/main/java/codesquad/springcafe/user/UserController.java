package codesquad.springcafe.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    UserDatabase userDatabase;
    private final UserCrednetialService userCrednetialService;

    @Autowired
    UserController(UserDatabase userDatabase, UserCrednetialService userCrednetialService) {
        this.userDatabase = userDatabase;
        this.userCrednetialService = userCrednetialService;
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (userDatabase.isExistUser(user.getUserId())) {
            redirectAttributes.addFlashAttribute("existId", true);
            redirectAttributes.addFlashAttribute("prevName", user.getName());
            redirectAttributes.addFlashAttribute("prevEmail", user.getEmail());
            return "redirect:/users/form";
        }
        userDatabase.addUser(user);
        logger.debug("add user : {}", user.getUserId());
        return "redirect:/";
    }

    @GetMapping("/users/form/{userid}")
    public String failCreateUser(@PathVariable String userid, Model model) {
        model.addAttribute("outputMessage", "이미 존재하는 회원입니다." + userid);
        return "user/create_failed";
    }

    @GetMapping("/users/list")
    public String showUsers(Model model) {
        List<User> userList = userDatabase.getUserList();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    @GetMapping("/users/{userid}")
    public String showUser(@PathVariable String userid, Model model) {
        User user = userDatabase.getUser(userid);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{userid}/form")
    public String showUserEditForm(@PathVariable String userid, Model model) {
        model.addAttribute("userid",  userid);
        return "user/updateForm";
    }

    @PutMapping("/users/{userid}")
    public String updateUser(@ModelAttribute UserLoginDTO userLoginDTO, // userid, pwd
                             @ModelAttribute User editedUser, // userid, pwd, name, email
                             RedirectAttributes redirectAttributes) {
        if (!userCrednetialService.checkValidCredential(userLoginDTO)) {
            redirectAttributes.addFlashAttribute("invalidInput", true);
            redirectAttributes.addFlashAttribute("prevName", editedUser.getName());
            redirectAttributes.addFlashAttribute("prevEmail", editedUser.getEmail());
            return "redirect:/users/" + userLoginDTO.getUserid() + "/form";
        }
        userDatabase.updateUser(editedUser);
        logger.info("update user : {}", editedUser.getUserId());
        return "redirect:/users/{userid}";
    }
}
