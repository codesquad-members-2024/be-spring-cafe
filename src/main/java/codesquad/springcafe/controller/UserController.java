package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserDto;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.error.exception.UserNotFoundException;
import codesquad.springcafe.error.exception.UserUnauthorizedException;
import codesquad.springcafe.repository.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String create(@ModelAttribute UserDto userDto) {
        userRepository.createUser(userDto);

        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable("userId") String userId, Model model) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자의 프로필을 조회할 수 없습니다."));

        model.addAttribute("user", user);
        logger.debug("프로필 조회: {}", user.toDto());

        return "user/profile";
    }

    @GetMapping("/{userId}/update")
    public String updateForm(@PathVariable("userId") String userId, HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        logger.debug("로그인 사용자: " + loginUser.toDto());

        // 세션에 사용자의 ID가 없거나, 요청 파라미터의 ID와 다르면 /로 리다이렉트
        if (loginUser == null || !loginUser.matchUserId(userId)) {
            throw new UserUnauthorizedException("프로필 수정에 대한 접근 권한이 없습니다.");
        }

        return "user/update";
    }

    @PutMapping("/{userId}/update")
    public String update(@PathVariable("userId") String userId, @ModelAttribute UserUpdateDto userUpdateDto,
                         Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("user");

        if (!loginUser.matchPassword(userUpdateDto.getPassword())) {
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "user/update";
        }

        userRepository.updateUser(loginUser.getUserId(), userUpdateDto);

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, Model model, HttpSession session) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId + "를 가진 사용자는 존재하지 않습니다."));

        if (!user.matchPassword(password)) {
            model.addAttribute("errorMessage", "비밀번호가 올바르지 않습니다.");
            return "user/login";
        }

        session.setAttribute("user", user);
        logger.debug("로그인 성공: {}", user.toDto());

        String redirectUrl = (String) session.getAttribute("redirectUrl");
        if (redirectUrl != null) {
            session.removeAttribute("redirectUrl");
            return "redirect:" + redirectUrl;
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        logger.debug("로그아웃: {}", loginUser.toDto());
        session.invalidate();

        return "redirect:/";
    }
}