package codesquad.springcafe.web.controller;

import codesquad.springcafe.service.UserService;
import codesquad.springcafe.web.dto.UserCreateDto;
import codesquad.springcafe.web.dto.UserUpdateDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String join(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "/user/form";
    }

    @PostMapping("/create")
    public String join(@ModelAttribute("user") UserCreateDto userCreateDto, BindingResult bindingResult) {
        if (!StringUtils.hasText(userCreateDto.getUserId())) {
            bindingResult.addError(new FieldError("user", "userId", userCreateDto.getUserId(),
                    false, null, null, "아이디를 입력하세요."));
        }
        if (!StringUtils.hasText(userCreateDto.getPassword())) {
            bindingResult.addError(new FieldError("user", "password", userCreateDto.getPassword(),
                    false, null, null, "비밀번호를 입력하세요."));
        }
        if (!StringUtils.hasText(userCreateDto.getPasswordCheck())) {
            bindingResult.addError(new FieldError("user", "passwordCheck", userCreateDto.getPasswordCheck(),
                    false, null, null, "비밀번호 확인을 입력하세요."));
        }
        if (!userCreateDto.getPassword().equals(userCreateDto.getPasswordCheck())) {
            bindingResult.addError(new FieldError("user", "passwordCheck", userCreateDto.getPasswordCheck(),
                    false, null, null, "비밀번호가 일치하지 않습니다."));
        }
        if (!StringUtils.hasText(userCreateDto.getName())) {
            bindingResult.addError(new FieldError("user", "name", userCreateDto.getName(),
                    false, null, null, "사용자 이름을 입력하세요."));
        }
        if (!StringUtils.hasText(userCreateDto.getEmail())) {
            bindingResult.addError(new FieldError("user", "email", userCreateDto.getEmail(),
                    false, null, null, "이메일을 입력하세요."));
        }
        if (bindingResult.hasErrors()) {
            return "/user/form";
        }
        userService.join(userCreateDto);
        return "redirect:/users";
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getUsers().values());
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String userDetails(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findOne(userId));
        return "/user/profile";
    }

    @GetMapping("/{userId}/update")
    public String userUpdate(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findOne(userId));
        return "/user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String userUpdate(@PathVariable String userId, @ModelAttribute UserUpdateDto userUpdateDto) {
        userService.updateUser(userId, userUpdateDto);
        return "redirect:/users";
    }
}
