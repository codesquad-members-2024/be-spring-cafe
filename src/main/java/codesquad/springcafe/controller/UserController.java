package codesquad.springcafe.controller;

import codesquad.springcafe.dto.User;
import codesquad.springcafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users") // 공유매핑
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired // 생성자가 하나일 때 생략 가능하지만, 우선 남겨둠 ...!
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 목록 조회하기
    @GetMapping("")
    public String users(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users); // users 를 "users" 라는 이름으로 등록 => DB 에서 가져온 데이터를 users 라는 이름으로 뷰 페이지에서 사용 가능
        return "users/list";
    }

    // 회원 가입 양식
    @GetMapping("/signup")
    public String showSignUpForm() { // 뷰로 데이터 전달
        return "users/form";
    }

    // 회원 가입 처리
    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) { // 서버에서 폼 데이터 처리
        User savedUser = userService.signup(user);
        logger.info("user.toString = {}", user.toString());
        redirectAttributes.addAttribute("id", savedUser.getUserId());
        return "redirect:/users/{id}";
    }

    // 회원 상세 조회
    @GetMapping("{id}")
    public String showUserDetail(@PathVariable("id") String id, Model model) {
        Optional<User> userOptional = userService.findUserByUserId(id);

        if (!userOptional.isPresent()) {
            logger.error("User with id {} not found", id);
            return "redirect:/users/list";
        }

        User user = userOptional.get();
        model.addAttribute("user", user);
        return "users/profile";
    }

    // 회원정보 수정 화면
    @GetMapping("{id}/edit")
    public String showEditForm(@PathVariable("id") String id, Model model){
        Optional<User> userOptional = userService.findUserByUserId(id);

        if (!userOptional.isPresent()){
            logger.info("User with id {} not found", id);
            return "redirect:/users/users";
        }

        model.addAttribute("user", userOptional.get());
        return "users/form_update";
    }

    // 회원 정보 수정 처리
    @PutMapping("{id}")
    public String updateUser(@PathVariable("id") String id, @ModelAttribute User user, RedirectAttributes redirectAttributes) {
        User updatedUser = userService.updateUser(id, user);
        logger.info("updated user={}", updatedUser);
        redirectAttributes.addAttribute("id", updatedUser.getUserId());
        return "redirect:/users";
    }
}
