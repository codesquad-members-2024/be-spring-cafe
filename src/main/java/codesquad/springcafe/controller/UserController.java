package codesquad.springcafe.controller;

import codesquad.springcafe.model.User;
import codesquad.springcafe.db.MemoryUserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final MemoryUserDatabase memoryUserDatabase;

    @Autowired
    public UserController(MemoryUserDatabase memoryUserDatabase){
        this.memoryUserDatabase = memoryUserDatabase;
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
        memoryUserDatabase.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model){
        List<User> users = memoryUserDatabase.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("totalUserNumber", memoryUserDatabase.getTotalUserNumber());
        return "users/list";
    }

    @GetMapping("/users/{userId}")
    public String userProfile(@PathVariable String userId, Model model){
        Optional<User> user = memoryUserDatabase.findUserByUserId(userId);
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
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        User user = getUserOrFail(userId);
        if(!user.isPasswordInputCorrect(password)){
            model.addAttribute("user", user);
            model.addAttribute("passwordError", true);
            return "users/updateForm";
        }

        user.setEmail(email);
        return "redirect:/users";
    }

    private User getUserOrFail(String userId){
        return memoryUserDatabase.findUserByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
