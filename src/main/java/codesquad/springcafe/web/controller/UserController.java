package codesquad.springcafe.web.controller;

import codesquad.springcafe.service.UserService;
import codesquad.springcafe.web.dto.UserCreateDto;
import codesquad.springcafe.web.dto.UserUpdateDto;
import codesquad.springcafe.web.validation.UserCreateValidator;
import codesquad.springcafe.web.validation.UserUpdateValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserCreateValidator userCreateValidator;
    private final UserUpdateValidator userUpdateValidator;

    public UserController(UserService userService, UserCreateValidator userCreateValidator, UserUpdateValidator userUpdateValidator) {
        this.userService = userService;
        this.userCreateValidator = userCreateValidator;
        this.userUpdateValidator = userUpdateValidator;
    }

    @InitBinder("create")
    public void initTargetCreate(WebDataBinder dataBinder) {
        dataBinder.addValidators(userCreateValidator);
    }

    @InitBinder("update")
    public void initTargetUpdate(WebDataBinder dataBinder) {
        dataBinder.addValidators(userUpdateValidator);
    }

    @GetMapping("/create")
    public String join(Model model) {
        model.addAttribute("create", new UserCreateDto());
        return "user/form";
    }

    @PostMapping("/create")
    public String join(@Validated @ModelAttribute("create") UserCreateDto userCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/form";
        }
        userService.join(userCreateDto);
        return "redirect:/users";
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String userDetails(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findOne(userId));
        return "user/profile";
    }

    @GetMapping("/{userId}/update")
    public String userUpdate(@PathVariable String userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("update", new UserUpdateDto());
        return "user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String userUpdate(@Validated @ModelAttribute("update") UserUpdateDto userUpdateDto, BindingResult bindingResult, @PathVariable String userId, Model model) {
        if (!userService.findOne(userId).getPassword().equals(userUpdateDto.getCurrentPassword())) {
            bindingResult.rejectValue("currentPassword", "temporary", null, "비밀번호가 일치하지 않습니다.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", userId);
            return "user/updateForm";
        }
        userService.updateUser(userId, userUpdateDto);
        return "redirect:/users";
    }
}
