package springcafe.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springcafe.user.dto.ChageInfoForm;
import springcafe.user.dto.UserCreateForm;
import springcafe.user.exception.WrongPasswordException;
import springcafe.user.model.User;
import springcafe.user.service.UserService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String userList(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    @GetMapping("/{userId}/form")
    public String updateInfo(@PathVariable String userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "user/updateform";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable String userId, Model model) {

        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/signup")
    public String singUp(UserCreateForm userCreateForm) {
        return "user/form";
    }

    @PostMapping("/signup")
    public String signUp(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/form";
        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "user/form";
        }

        userService.create(userCreateForm.getUserId(), userCreateForm.getPassword()
                , userCreateForm.getName(), userCreateForm.getEmail());

        return "redirect:/user/list";
    }

    @GetMapping("/update")
    public String updateForm(ChageInfoForm chageInfoForm, HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "user/update";
    }


    @PostMapping("/update")
    public String updateInfo(@Valid ChageInfoForm chageInfoForm, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "redirect:/user/update";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (!userService.checkHashPassword(chageInfoForm, user.getPassword())) {
            throw new WrongPasswordException("비밀번호가 잘못됐습니다.");
        }

        userService.updateInfo(user.getUserId(), chageInfoForm.getName(), chageInfoForm.getEmail());

        return "redirect:/user/list";
    }

    @GetMapping("/{userId}/exists")

    public ResponseEntity<?> checkUserIdExists(@PathVariable String userId) {
        boolean isDuplicate = userService.checkUserIdExists(userId);
        return ResponseEntity.ok(Collections.singletonMap("isDuplicate", isDuplicate));
    }
}
