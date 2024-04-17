package codesquad.springcafe.controller;

import codesquad.springcafe.db.UserDatabase;
import codesquad.springcafe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserDatabase userDatabase;

    @Autowired
    public UserController(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
        userDatabase.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model){
        List<User> users = userDatabase.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("totalUserNumber", userDatabase.getTotalUserNumber());
        return "users/list";
    }

    @GetMapping("/users/{userId}")
    public String userProfile(@PathVariable String userId, Model model){
        Optional<User> user = userDatabase.findUserByUserId(userId);
        if(user.isEmpty()){
            throw new IllegalArgumentException();
        }
        model.addAttribute("user", user.get());
        return "users/profile";
    }

    @GetMapping("/users/{userId}/update")
    public String getProfileEditPage(@PathVariable String userId, Model model){
        User user = getUserOrFail(userId);
        model.addAttribute("user", user);
        return "users/updateForm";
    }

    @PutMapping("/users/{userId}/update")
    public String updateProfile(
            @PathVariable String userId,
            @RequestParam String nickname,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        User user = getUserOrFail(userId);
        if(!user.isPasswordInputCorrect(password)){
            model.addAttribute("user", user);
            model.addAttribute("passwordError", true);
            return "users/updateForm";
        }
        user.setNickname(nickname);
        user.setEmail(email);
        userDatabase.update(user.getUserId(), user);
        return "redirect:/users";
    }

    private User getUserOrFail(String userId){
        return userDatabase.findUserByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
