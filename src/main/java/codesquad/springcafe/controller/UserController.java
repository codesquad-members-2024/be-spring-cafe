package codesquad.springcafe.controller;

import codesquad.springcafe.db.user.UserDatabase;
import codesquad.springcafe.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public String userProfile(
            @PathVariable String userId,
            HttpServletResponse response,
            Model model) throws IOException {
        Optional<User> user = userDatabase.findUserByUserId(userId);
        if(user.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        model.addAttribute("user", user.get());
        return "users/profile";
    }

    @GetMapping("/users/{userId}/update")
    public String getProfileEditPage(
            @PathVariable String userId,
            Model model,
            HttpServletResponse response) throws IOException {
        Optional<User> user = userDatabase.findUserByUserId(userId);
        if(user.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        model.addAttribute("user", user);
        return "users/updateForm";
    }

    @PutMapping("/users/{userId}/update")
    public String updateProfile(
            @PathVariable String userId,
            @RequestParam String nickname,
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            HttpServletResponse response) throws IOException {
        Optional<User> tmpUser = userDatabase.findUserByUserId(userId);
        if(tmpUser.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        User user = tmpUser.get();
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

}
