package springcafe.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springcafe.user.dto.UserCreateForm;
import springcafe.user.dto.UserDto;
import springcafe.user.model.User;
import springcafe.user.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {


    @GetMapping("update")
    public String update() {
        return "user/updateform";
    }
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public String login(HttpServletRequest request, HttpSession session) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        boolean isValidIdAndPassword = userService.loginCheck(userId, password);

        if (isValidIdAndPassword == false) {
            return "user/login.failed";
        }

        session.setAttribute("userId", userId);
        return "user/list";
    }

    @GetMapping("/list")
    public String userList(Model model) {
        List<UserDto> userDto = userService.findAll();
        model.addAttribute("userList", userDto);
        return "user/list";
    }

    @GetMapping("/{userId}/form")
    public String update(@PathVariable String userId, Model model) {
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
    public String singUp(UserCreateForm userCreateForm){
        return "user/form";
    }

    @PostMapping("/signup")
    public String signUp(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "user/form";
        }

        if(!userCreateForm.getPassword().equals(userCreateForm.getPasswordConfirm())){
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "user/form";
        }

        userService.create(userCreateForm.getUserId(), userCreateForm.getPassword()
                , userCreateForm.getName(), userCreateForm.getEmail());

        return "redirect:/user/list";
    }


    @PostMapping("/update")
    public String update(User user) {
       userService.updateInfo(user.getUserId(), user.getPassword(), user.getName(), user.getEmail());

       return "redirect:/user/list";
    }

}
