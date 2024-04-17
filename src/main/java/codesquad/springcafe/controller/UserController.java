package codesquad.springcafe.controller;

import codesquad.springcafe.model.User;
import codesquad.springcafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * 회원 목록 조회 : GET '/users'
 * 회원 등록 : POST '/users'
 * 회원 조회 : GET '/users/{userId}'
 * 회원 수정 : PATCH '/users/{userId}'
 * 회원 삭제 : DELETE '/users/{userId}'
 */

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String user() {
        return "users/form";
    }

    @PostMapping("/create")
    public String join(@ModelAttribute User user) {
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "users/list";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findOne(userId));
        return "users/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        model.addAttribute("id", userService.findOne(userId));
        return "users/updateForm";
    }


    @PutMapping("/{userId}/form")
    public String userUpdate(@PathVariable String userId, @ModelAttribute User updatedUser, Model model) {
        Optional<User> optionalUser = userService.findOne(userId);
        optionalUser.ifPresent(user -> {
                    user.setName(updatedUser.getName());
                    user.setPassword(updatedUser.getPassword());
                    user.setEmail(updatedUser.getEmail());
                    userService.userUpdate(user);
                }
        );
        return "redirect:/users";
    }
}
