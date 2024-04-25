package codesquad.springcafe.controller;

import codesquad.springcafe.db.user.UserDatabase;
import codesquad.springcafe.exceptions.UserNotFoundException;
import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserCreationDto;
import codesquad.springcafe.model.user.dto.UserCredentialDto;
import codesquad.springcafe.model.user.dto.UserProfileDto;
import codesquad.springcafe.model.user.dto.UserProfileEditDto;
import codesquad.springcafe.security.PasswordEncoder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserDatabase userDatabase;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserDatabase userDatabase, PasswordEncoder passwordEncoder){
        this.userDatabase = userDatabase;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/add")
    public String getUserCreationForm(){
        return "users/form";
    }

    @PostMapping("/add/duplication-check")
    public ResponseEntity<?> checkDuplication(@RequestParam String field, @RequestParam String value){
        boolean isDuplicated = isValueDuplicated(field, value);
        Map<String, String> response = new HashMap<>();
        if (isDuplicated) {
            response.put("status", "fail");
            return ResponseEntity.ok(response);
        }
        response.put("status", "pass");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public String createUser(@ModelAttribute UserCreationDto userCreationDto, Model model) {
        User user = userCreationDto.toEntity();

        String[] fields = {"userId", "email", "nickname"};
        String[] values = {user.getUserId(), user.getEmail(), user.getNickname()};
        if(hasDuplicationInFields(fields, values)){
            model.addAttribute("duplicationError", true);
            return "users/form";
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDatabase.addUser(user);
        return "redirect:/";
    }

    @GetMapping
    public String userList(Model model){
        List<UserProfileDto> users = userDatabase.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("totalUserNumber", userDatabase.getTotalUserNumber());
        return "users/list";
    }

    private boolean profilePopulationSucceed(String userId, Model model)  {
        Optional<UserProfileDto> userProfileOpt = userDatabase.findUserByUserId(userId);
        if (userProfileOpt.isEmpty()) {
            return false;
        }
        model.addAttribute("user", userProfileOpt.get());
        return true;
    }

    @GetMapping("detail/{userId}")
    public String userProfile(@PathVariable String userId, Model model) {
        if(!profilePopulationSucceed(userId, model)){
           throw new UserNotFoundException();
        }
        return "users/profile";
    }

    @GetMapping("detail/{userId}/update")
    public String getProfileEditPage(@PathVariable String userId, Model model) {
        if(!profilePopulationSucceed(userId, model)){
            throw new UserNotFoundException();
        }
        return "users/updateForm";
    }

    @PutMapping("detail/{userId}/update")
    public String updateProfile(@PathVariable String userId, @ModelAttribute UserProfileEditDto userInput, Model model) {
        UserCredentialDto userCredential = userDatabase.getUserCredential(userId)
                .orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(userInput.getPassword(), userCredential.getPassword())){
            model.addAttribute("user", userInput);
            model.addAttribute("passwordError", true);
            return "users/updateForm";
        }

        String[] fields = {"email", "nickname"};
        String[] values = {userInput.getEmail(), userInput.getNickname()};
        if(hasDuplicationInFields(fields, values)){
            model.addAttribute("user", userInput);
            model.addAttribute("duplicationError", true);
            return "users/updateForm";
        }

        userDatabase.update(userInput.getUserId(), userInput);
        return "redirect:/users";
    }

    private boolean hasDuplicationInFields(String[] fields, String[] values){
        for (int i = 0; i < fields.length; i++) {
            if (isValueDuplicated(fields[i], values[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isValueDuplicated(String field, String value){
        return switch (field) {
            case "userId" -> userDatabase.existsByUserId(value);
            case "email" -> userDatabase.existsByEmail(value);
            case "nickname" -> userDatabase.existsByNickname(value);
            default -> false;
        };
    }
}
