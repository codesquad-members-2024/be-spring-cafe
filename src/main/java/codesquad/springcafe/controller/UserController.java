package codesquad.springcafe.controller;

import codesquad.springcafe.db.user.UserDatabase;
import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserCreationDto;
import codesquad.springcafe.model.user.dto.UserProfileEditDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserDatabase userDatabase;

    @Autowired
    public UserController(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
    }

    @PostMapping("/add")
    public String createUser(@ModelAttribute UserCreationDto userCreationDto) {
        userDatabase.addUser(userCreationDto.toEntity());
        return "redirect:/";
    }

    @GetMapping
    public String userList(Model model){
        List<User> users = userDatabase.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("totalUserNumber", userDatabase.getTotalUserNumber());
        return "users/list";
    }

    @GetMapping("detail/{userId}")
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

    @GetMapping("detail/{userId}/update")
    public String getProfileEditPage(
            @PathVariable String userId,
            Model model,
            HttpServletResponse response) throws IOException {
        Optional<User> user = userDatabase.findUserByUserId(userId);
        if(user.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        model.addAttribute("user", user.get());
        return "users/updateForm";
    }

    @PutMapping("detail/{userId}/update")
    public String updateProfile(
            @PathVariable String userId,
            @ModelAttribute UserProfileEditDto userProfileEditDto,
            Model model,
            HttpServletResponse response) throws IOException {
        Optional<User> tmpUser = userDatabase.findUserByUserId(userId);
        if(tmpUser.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        User user = tmpUser.get();
        if(!user.isPasswordInputCorrect(userProfileEditDto.getPassword())){
            model.addAttribute("user", user);
            model.addAttribute("passwordError", true);
            return "users/updateForm";
        }
        user.setNickname(userProfileEditDto.getNickname());
        user.setEmail(userProfileEditDto.getEmail());
        userDatabase.update(user.getUserId(), user);
        return "redirect:/users";
    }

}
