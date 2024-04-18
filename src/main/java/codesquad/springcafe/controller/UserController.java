package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserDto;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(Model model) {
        List<User> users = userRepository.findAllUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/create")
    public String createForm() {
        return "user/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDto userDto) {
        User newUser = userRepository.createUser(userDto);
        logger.info("회원가입 성공: {}", newUser.toDto());
        return "redirect:/users"; // 이 uri로 리다이렉트
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable("userId") String userId, Model model) {
        Optional<User> optionalUser = userRepository.findUserByUserId(userId);
        if (optionalUser.isPresent()) {
            User findedUser = optionalUser.get();
            model.addAttribute("user", findedUser);
            logger.info("사용자 프로필 조회: {}", findedUser.toDto());
            return "user/profile";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping("/{userId}/update")
    public String updateForm(@PathVariable("userId") String userId) {
        return "user/update";
    }

    @PutMapping("/{userId}/update")
    public String update(@PathVariable("userId") String userId, @ModelAttribute UserUpdateDto userUpdateDto) {
        User updatedUser = userRepository.updateUser(userId, userUpdateDto);
        logger.info("업데이트 성공: {}", updatedUser.toDto());
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userId") String userId, @RequestParam("password") String password) {
        Optional<User> optionalUser = userRepository.findUserByUserId(userId);
        if (optionalUser.isPresent()) {
            User loginedUser = optionalUser.get();
            if (loginedUser.getPassword().equals(password)) {
                logger.info("로그인 성공: {}", loginedUser.toDto());
                return "redirect:/";
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 조회 실패");
        }
        return null;
    }
}