package codesquad.springcafe.controller;

import codesquad.springcafe.dto.user.SignUpDTO;
import codesquad.springcafe.dto.user.UserInfoDTO;
import codesquad.springcafe.dto.user.UserUpdateDTO;
import codesquad.springcafe.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String signUp(@ModelAttribute("user") SignUpDTO signUpDTO, Model model) {
        UserInfoDTO newUser = userService.signUp(signUpDTO);
        model.addAttribute("user", newUser);
        return "redirect:/users";
    }

    @GetMapping
    public String showList(Model model) {
        List<UserInfoDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        UserInfoDTO targetUser = userService.findUserById(userId);
        model.addAttribute("user", targetUser);
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateInfoForm(@PathVariable String userId,
        Model model) {
        UserInfoDTO targetUser = userService.findUserById(userId);
        model.addAttribute("user", targetUser);
        return "/user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String updateInfo(@PathVariable String userId,
        @ModelAttribute("user") UserUpdateDTO updateInfo, Model model) {
        UserInfoDTO updatedUser = userService.updateInfo(userId, updateInfo);
        model.addAttribute("user", updatedUser);
        return "redirect:/users";
    }
}