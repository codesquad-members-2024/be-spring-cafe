package codesquad.springcafe.controller;

import codesquad.springcafe.db.user.UserDatabase;
import codesquad.springcafe.model.user.dto.UserCreationDto;
import codesquad.springcafe.model.user.dto.UserCredentialDto;
import codesquad.springcafe.model.user.dto.UserProfileDto;
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
        List<UserProfileDto> users = userDatabase.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("totalUserNumber", userDatabase.getTotalUserNumber());
        return "users/list";
    }

    @GetMapping("detail/{userId}")
    public String userProfile(
            @PathVariable String userId,
            HttpServletResponse response,
            Model model) throws IOException {
        Optional<UserProfileDto> dto = userDatabase.findUserByUserId(userId);
        if(dto.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        model.addAttribute("user", dto.get());
        return "users/profile";
    }

    @GetMapping("detail/{userId}/update")
    public String getProfileEditPage(
            @PathVariable String userId,
            Model model,
            HttpServletResponse response) throws IOException {
        Optional<UserProfileDto> dto = userDatabase.findUserByUserId(userId);
        if(dto.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        model.addAttribute("user", dto.get());
        return "users/updateForm";
    }

    @PutMapping("detail/{userId}/update")
    public String updateProfile(
            @PathVariable String userId,
            @ModelAttribute UserProfileEditDto userProfileEditDto,
            Model model,
            HttpServletResponse response) throws IOException {
        Optional<UserCredentialDto> userCredentialDto = userDatabase.getUserCredential(userId);
        if(userCredentialDto.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        UserCredentialDto userCredential = userCredentialDto.get();
        if(!userProfileEditDto.getPassword().equals(userCredential.getPassword())){
            model.addAttribute("user", userProfileEditDto);
            model.addAttribute("passwordError", true);
            return "users/updateForm";
        }
        userDatabase.update(userProfileEditDto.getUserId(), userProfileEditDto);
        return "redirect:/users";
    }

}
