package codesquad.springcafe.user;

import codesquad.springcafe.post.PostController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        logger.info("Showing registration form");
        return "/users/form";
    }

    @GetMapping("/list")
    public String showUserListForm(Model model){
        model.addAttribute("users",userService.findAll());
        logger.info("Showing user list");
        return "/users/list";
    }

    @PostMapping("/create")
    public String createUser(User user)
    {
        userService.createUser(user);
        logger.info("User created {}", user);
        return "redirect:/users/list";
    }

    @GetMapping("/{userId}/profile")
    public String getUserProfile(@PathVariable("userId") String userId, Model model) {
        User user = userService.findByUserId(userId);
        if (user != null) {
            logger.info("User found {}", user);
            model.addAttribute("user", user);
            return "/users/profile";
        }
        return "redirect:/users/list";
    }

    @GetMapping("/{userId}/update")
    public String showUserUpdateForm(@PathVariable("userId") String userId, Model model){
        User user = userService.findByUserId(userId);
        if (user != null) {
            logger.info("Update User found {}", user);
            model.addAttribute("user", user);
            return "/users/updateform";
        }
        return "redirect:/users/list";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(@PathVariable("userId") String userId,
                             @ModelAttribute("user") User user,
                             @RequestParam("currentPassword") String currentPassword,
                             Model model) {

        // 현재 비밀번호 확인
        User currentUser = userService.findByUserId(userId);
        if (currentUser.validateCurrentPassword (currentPassword)) {
            userService.updateUser(userId, user);
            logger.info("User updated {}", user);
            return "redirect:/users/list";
        }else{
            model.addAttribute("error", true);
            model.addAttribute("user", currentUser);
            logger.info("CurrentPassword is different");
            return "/users/updateform";
        }
    }
}
