package codesquad.springcafe.controller;

import codesquad.springcafe.dto.UserJoinDto;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.exception.service.DuplicateUserIdException;
import codesquad.springcafe.model.ListUser;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.model.UpdatedUser;
import codesquad.springcafe.model.User;
import codesquad.springcafe.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/join")
    public String showJoinPage(@ModelAttribute("userJoinDto") UserJoinDto userJoinDto) {
        return "user/join";
    }

    @PostMapping("/join")
    public String processJoinForm(@Valid @ModelAttribute UserJoinDto userJoinDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/join";
        }
        try {
            User user = userJoinDto.creatUser();
            userService.addUser(user);
            return "redirect:/users/join/success";
        } catch (DuplicateUserIdException e) {
            bindingResult.rejectValue("userId", "duplicatedId");
            return "user/join";
        }
    }

    @GetMapping("/join/success")
    public String showSuccessJoinPage(Model model) {
        model.addAttribute("navbarFlag", false);
        return "user/join_success";
    }

    @GetMapping("/list")
    public String showListPage(Model model) {
        List<ListUser> users = userService.findAllUser();
        model.addAttribute("userList", users);
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String showProfilePage(Model model, @PathVariable String userId) {
        SessionUser sessionUser = userService.findSessionUserById(userId);
        model.addAttribute("user", sessionUser);
        return "user/profile";
    }

    @GetMapping("/match-pw/{userId}")
    public String showMatchPasswordPage(Model model, @PathVariable String userId, HttpSession httpSession) {
        Object matchedPwFlag = httpSession.getAttribute("matchPw");
        if (matchedPwFlag != null && (boolean) matchedPwFlag) { // 비밀번호 일치 여부 검증을 끝냈다면
            return "redirect:/users/update/{userId}";
        }
        model.addAttribute("userId", userId);
        return "user/match_pw";
    }

    @PostMapping("/match-pw/{userId}")
    public String processMatchPasswordForm(@PathVariable String userId, @RequestParam String userPassword,
                                           HttpSession httpSession) {
        User user = userService.findUserByUserId(userId);
        if (user.matchPassword(userPassword)) { // 비밀번호 일치 성공
            httpSession.setAttribute("matchPw", true);
            return "redirect:/users/update/{userId}";
        }
        return "redirect:/users/match-pw/{userId}?error";
    }

    @GetMapping("/update/{userId}")
    public String showUpdatePage(@ModelAttribute("userUpdateDto") UserUpdateDto userUpdateDto, Model model,
                                 @PathVariable String userId, HttpSession httpSession) {
        Object matchedPwFlag = httpSession.getAttribute("matchPw");
        if (matchedPwFlag != null && (boolean) matchedPwFlag) { // 비밀번호 일치 여부 검증을 끝냈다면
            model.addAttribute("userId", userId);
            return "user/update";
        }
        return "redirect:/users/match-pw/{userId}";
    }

    @PutMapping("/update/{userId}")
    public String updateUserInfo(@Valid @ModelAttribute UserUpdateDto userUpdateDto, BindingResult bindingResult,
                                 @PathVariable String userId) {
        if (bindingResult.hasErrors()) {
            return "user/update";
        }
        UpdatedUser updatedUser = userUpdateDto.creatUpdateUser();
        userService.updateUser(userId, updatedUser);
        return "redirect:/users/list";
    }

    @GetMapping("/invalid-modify")
    public String showInvalidModifyPage(Model model) {
        model.addAttribute("errorMsg", "다른 사람의 개인 정보는 수정할 수 없습니다.");
        return "error/form";
    }
}
