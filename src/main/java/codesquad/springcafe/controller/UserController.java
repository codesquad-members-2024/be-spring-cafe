package codesquad.springcafe.controller;

import codesquad.springcafe.repository.UserRepository;
import codesquad.springcafe.dto.UpdateUser;
import codesquad.springcafe.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/register")
    public String showForm() {
        return "user/register/form";
    }

    @PostMapping("/user/register")
    public String register(User user) {
        // todo 유저 중복 체크 필요
        userRepository.add(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String showList(Model model) {
        model.addAttribute("users", userRepository.getAll());
        return "user/list";
    }

    @GetMapping("/user/profile/{userId}")
    public String showProfile(Model model, @PathVariable("userId") String userId) {
        model.addAttribute("user", userRepository.getById(userId));
        return "user/profile";
    }

    @GetMapping("/user/profile/{userId}/update")
    public String showEditProfileForm(Model model, @PathVariable("userId") String userId) {
        model.addAttribute("user", userId);
        return "user/updateForm";
    }

    @PutMapping("/user/profile/{userId}/update")
    public String editProfile(UpdateUser updateUser, @PathVariable("userId") String userId, Model model) {
        User target = userRepository.getById(userId);
        String password = updateUser.getPassword();

        if (target.checkPassword(password)) {
            target.setPassword(updateUser.getNewPassword());
            target.setName(updateUser.getName());
            target.setEmail(updateUser.getEmail());
            userRepository.update(target);
            return "redirect:/user/list";
        } else {
            model.addAttribute("error", true);
            model.addAttribute("userEdit", updateUser);
            return "user/updateForm";
        }
    }
}
